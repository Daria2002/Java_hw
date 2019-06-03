<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript">
function setFontColor() {
	window.setTimeout( "setFontColor()", 5000); // 5000 milliseconds delay
	
	var index = Math.round(Math.random() * 9);
	
	var ColorValue = "FFFF00"; // default color - yellow
	
	if(index == 1)
	ColorValue = "000000"; // black
	if(index == 2)
	ColorValue = "0000FF"; // blue
	if(index == 3)
	ColorValue = "008000"; // green
	if(index == 4)
	ColorValue = "4B0082"; // purple
	if(index == 5)
	 ColorValue = "8B0000"; // red
	if(index == 6)
	ColorValue = "C71585"; // pink
	if(index == 7)
	ColorValue = "FF8C00"; // orange
	if(index == 8)
	ColorValue = "FFFF00"; // yellow
	if(index == 9)
	ColorValue = "696969"; // grey
	
	document.getElementsByTagName("body")[0].style.color = "#" + ColorValue;
}
</script>
<body onload="setFontColor();">

<html>
<head>
<meta charset="UTF-8">
<title>Story</title>
</head>
<body>
	<b><p>
	
	One bright day in late autumn a family of Ants were bustling about in the warm 
	sunshine, drying out the grain they had stored up during the summer, when a 
	starving Grasshopper, his fiddle under his arm, came up and humbly begged for 
	a bite to eat. "What!" cried the Ants in surprise, "haven't you stored anything 
	away for the winter? What in the world were you doing all last summer?"
	"I didn't have time to store up any food," whined the Grasshopper; 
	"I was so busy making music that before I knew it the summer was gone."
	The Ants shrugged their shoulders in disgust. "Making music, were you?" they cried. 
	"Very well; now dance!" And they turned their backs on the Grasshopper and went 
	on with their work.

	</p></b>
</body>
</html>