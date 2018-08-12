# regex-matcher
A simple regular expression matching library<br>
<br>
Based on examples in Chapter 17 and 18 of 
<a href="https://www.amazon.com/Compiler-Construction-Using-Java-JavaCC/dp/0470949597">
Computer Construction Using Java, JavaCC, and Yacc by Anthony J. Dos Reis
</a>
##Usage Examples
```
String regex = "a*b*c*";
Matcher matcher = new Matcher(regex);
boolean isMatch = matcher.match("aaabbc");
```
