document.write("<div id='vote_257' class='votecontainer' style='text-align:left' >");
document.write(" <form id='voteForm_257' name='voteForm_257' action='http://localhost:8080/ZCMS/Services/VoteResult.jsp' method='post' target='_blank'>");
document.write(" <input type='hidden' id='ID' name='ID' value='257'>");
document.write(" <input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>");
document.write(" <dl>");
document.write("  <dt id='417'>1.您认为本网站的首页外观怎么样？</dt>");
document.write("<dd><label><input name='Subject_417' type='radio' value='1916' />不错</label></dd>");
document.write("<dd><label><input name='Subject_417' type='radio' value='1917' />一般</label></dd>");
document.write("<dd><label><input name='Subject_417' type='radio' value='1918' />有待改进</label></dd>");
document.write("  <dt id='425'>2.第二项测试</dt>");
document.write("<dd><label><input name='Subject_425' type='radio' value='2102' />A2</label></dd>");
document.write("<dd><label><input name='Subject_425' type='radio' value='2103' />A2</label></dd>");
document.write(" <dl>");
document.write(" <dd><input type='submit' value='提交' onclick='return checkVote(257);'>&nbsp;&nbsp<input type='button' value='查看' onclick='javascript:window.open(\"http://localhost:8080/ZCMS/Services/VoteResult.jsp?ID=257\",\"VoteResult\")'></dd>");
document.write(" </dl>");
document.write(" </form>");
document.write("</div>");
