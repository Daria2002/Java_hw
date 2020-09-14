package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.crypto.Data;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.NowNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents smart script engine that execute given script
 * @author Daria Matković
 *
 */
public class SmartScriptEngine {
	/** document node **/
	private DocumentNode documentNode;
	/** request context **/
	private RequestContext requestContext;
	/** multistack **/
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/** visitor **/
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Request context can't write node's text.");
				System.exit(0);
			}
		}

		/**
		 * Gets ElementConstantInteger or ElementConstantDouble depending on el
		 * @param el element to check
		 * @return ElementConstantInteger or ElementConstantDouble
		 */
		private Object getElement(Element el) {
			if(el instanceof ElementConstantInteger) {
				return ((ElementConstantInteger) el).getValue();
			}
			
			return ((ElementConstantDouble) el).getValue();
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			Object start = getElement(node.getStartExpression());
			
			ValueWrapper valueWrapper = new ValueWrapper(start);
			multistack.push(node.getVariable().getName(), valueWrapper);
			// double
			Object end = getElement(node.getEndExpression());
			Object step = getElement(node.getStepExpression());
			
			while(true) {
				ValueWrapper current = multistack.peek(node.getVariable().getName());
				
				if(Double.valueOf(current.getValue().toString()) - 
						Double.valueOf(end.toString()) > 0.00001) {
					break;
				}

				acceptChildren(node);
				
				current.add(step);
			}
			
			multistack.pop(node.getVariable().getName());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<Object>();
			
			for(Element token : node.getElements()) {
				Object value = getConstant(token);
				
				if(value != null) {
					stack.push(value);
					
					continue;
				}
				
				if(token instanceof ElementVariable) {
					stack.push(multistack.peek(((ElementVariable) token).getName()).getValue());
					
					continue;
				}
				
				if(token instanceof ElementOperator) {
					String op = ((ElementOperator) token).getSymbol();

					ValueWrapper valWrapper1 = new ValueWrapper(stack.pop());
					ValueWrapper valWrapper2 = new ValueWrapper(stack.pop());
					
					
					switch (op) {
					case "+":
						valWrapper1.add(valWrapper2.getValue());
						stack.push(new ValueWrapper(valWrapper1.getValue()).getValue());
						break;
					
					case "-":
						valWrapper1.subtract(valWrapper2.getValue());
						stack.push(new ValueWrapper(valWrapper1.getValue()).getValue());
						break;
					
					case "*":
						valWrapper1.multiply(valWrapper2.getValue());
						stack.push(new ValueWrapper(valWrapper1.getValue()).getValue());
						break;
					
					case "/":
						valWrapper1.divide(valWrapper2.getValue());
						stack.push(new ValueWrapper(valWrapper1.getValue()).getValue());
						break;
				
					default:
						throw new IllegalArgumentException("Invalid operator");
					}
					
					continue;
				}
				
				if(token instanceof ElementFunction) {
					String functionName = ((ElementFunction) token).getName();
					
					ValueWrapper valueWrapper1;
					
					switch (functionName) {
					case "sin":
						valueWrapper1 = new ValueWrapper(stack.pop());
						stack.push(sin(Double.valueOf(valueWrapper1.getValue().toString())));
						break;

					case "decfmt":
						String f = stack.pop().toString();
						valueWrapper1 = new ValueWrapper(stack.pop()); // x
						
						stack.push(decfmt(f, (Double)valueWrapper1.getValue()));
						break;
					
					case "now":
						String format = stack.pop().toString();
						
						stack.push(now(format));
						break;
						
					case "dup":
						dup(stack);
						break;

					case "swap":
						swap(stack);
						break;		
					
					case "setMimeType":
						setMimeType((String)stack.pop());
						break;
						
					case "paramGet":
						paramGet(stack);
						break;
						
					case "pparamGet":
						pparamGet(stack);
						break;
						
					case "pparamSet":
						pparamSet(stack);
						break;
					
					case "pparamDel":
						pparamDel(stack);
						break;
						
					case "tparamGet":
						tparamGet(stack);
						break;
						
					case "tparamSet":
						tparamSet(stack);
						break;
						
					case "tParamDel":
						tParamDel(stack);
						break;
					
					default:
						throw new IllegalArgumentException("Invalid function name: " + functionName);
					}
				}
			}
			
			try {
				
				if(stack.isEmpty()) {
					return;
				}
				
				List<String> stackElements = new ArrayList<String>();
				
				while(!stack.isEmpty()) {
					stackElements.add(stack.pop().toString());
				}
				
				for(int i = stackElements.size() - 1; i >= 0; i--) {
					requestContext.write(stackElements.get(i));
				}
				
			} catch (IOException e) {
				throw new IllegalArgumentException("Error occurred while writing to "
						+ "request context.");
			}
		}

		/**
		 * removes association for name from requestContext temporaryParameters map
		 * @param stack stack
		 */
		private void tParamDel(Stack<Object> stack) {
			requestContext.removeTemporaryParameter((String) stack.pop());
		}

		/**
		 * stores a value into requestContext temporaryParameters map
		 * @param stack stack
		 */
		private void tparamSet(Stack<Object> stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			requestContext.setTemporaryParameter(name, value);
		}

		/**
		 * same as paramGet but reads from requestContext temporaryParameters map
		 * @param stack stack
		 */
		private void tparamGet(Stack<Object> stack) {
			Object defValue = stack.pop();
			String name = (String) stack.pop();
			Object value = requestContext.getTemporaryParameter(name);
			stack.push(value == null ? defValue : value);
		}

		/**
		 * removes association for name from requestContext persistentParameters map
		 * @param stack stack
		 */
		private void pparamDel(Stack<Object> stack) {
			requestContext.removePersistentParameter((String) stack.pop());
		}

		/**
		 * stores a value into requestContext persistent parameters map
		 * @param stack stack
		 */
		private void pparamSet(Stack<Object> stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			requestContext.setPersistentParameter(name, value);
		}

		/**
		 * same as paramGet but reads from requestContext persistent parameters map
		 * @param stack stack
		 */
		private void pparamGet(Stack<Object> stack) {
			Object defValue = stack.pop();
			String name = (String) stack.pop();
			Object value = requestContext.getPersistentParameter(name);
			stack.push(value == null ? defValue : value);
		}

		/**
		 * Obtains from requestContext parameters map a value mapped for name
		 * and pushes it onto stack. If there is no such mapping,
		 * it pushes instead defValue onto stack
		 * @param stack stack
		 */
		private void paramGet(Stack<Object> stack) {
			Object defValue = stack.pop();
			String name = (String) stack.pop();
			Object value = requestContext.getParameter(name);
			stack.push(value == null ? defValue : value);
		}

		/**
		 * takes string x and calls requestContext.setMimeType(x).
		 * Does not produce any result
		 * @param x value to set
		 */
		private void setMimeType(String x) {
			requestContext.setMimeType(x);
		}

		/**
		 * replaces the order of two topmost items on stack
		 * @param stack stack
		 */
		private void swap(Stack<Object> stack) {
			Object a = stack.pop();
			Object b = stack.pop();
			stack.push(a);
			stack.push(b);
		}

		/**
		 * duplicates current top value from stack
		 * @param stack stack
		 */
		private void dup(Stack<Object> stack) {
			Object value = stack.pop();
			stack.push(value);
			stack.push(value);
		}

		/**
		 * formats decimal number using given format f which is compatible with 
		 * DecimalFormat; produces a string. X can be integer, double or string representation of a number
		 * @param f format
		 * @param x value
		 * @return formated value
		 */
		private Object decfmt(String f, Double x) {
			NumberFormat formatter = new DecimalFormat(f);     
			return formatter.format(x);
		}

		private Object now(String format) {
			System.out.println("sad je u funkciji now i format je = " + format);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
			String formatDateTime = LocalDateTime.now().format(dtf);
			
			return formatDateTime;
		}
		/**
		 * calculates sin of given value
		 * @param value value
		 * @return sin of given value
		 */
		private Object sin(double value) {
			return Math.sin(value * Math.PI/180);
		}

		/**
		 * Converts Element value to ElementConstantInteger or
		 * ElementConstantDouble or ElementConstantString
		 * @param el value
		 * @return ElementConstantInteger or ElementConstantDouble or ElementConstantString
		 */
		private Object getConstant(Element el) {
			if(el instanceof ElementConstantInteger) {
				return ((ElementConstantInteger) el).getValue();
				
			} else if(el instanceof ElementConstantDouble) {
				return ((ElementConstantDouble) el).getValue();
			
			} else if(el instanceof ElementString) {
				return ((ElementString) el).getValue();
			
			}
			return null;
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			acceptChildren(node);
		}
		
		/**
		 * Call accept all children of given node
		 * @param node node
		 */
		private void acceptChildren(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}


		@Override
		public void visitNowNode(NowNode node) {
			String format = node.getFormat();
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
			try {
				requestContext.write(now.format(dtf));
			} catch (Exception e) {
				System.out.println("greska kod pisanja vremena");
			}
		}
	};
	
	/**
	 * Constructor for smart script engine
	 * @param documentNode document node
	 * @param requestContext request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext
			requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Calls execute on document node
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
