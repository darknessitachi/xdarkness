function ZCMSAD(PositionID) {
  this.ID        = PositionID;
  this.PosID  = 0; 
  this.ADID		  = 0;
  this.ADType	  = "";
  this.ADName	  = "";
  this.ADContent = "";
  this.PaddingLeft = 0;
  this.PaddingTop  = 0;
  this.Width = 0;
  this.Height = 0;
  this.IsHitCount = "N";
  this.UploadFilePath = "";
  this.URL = "";
  this.SiteID = 0;
  this.ShowAD  = showADContent;
}

function adClick(ADID,ADURL) {
	var sp = document.createElement("SCRIPT");
	sp.src = this.URL+"?SiteID="+this.SiteID+"&ADID="+ADID+"&URL="+ADURL;
	document.body.appendChild(sp);
}

function showADContent() {
  var content = this.ADContent;
  var str = "<div id='ZCMSAD_"+this.PosID+"'>";
  var AD = eval('('+content+')');
  var count = 0;
  if(AD.ADImage.length){
	  count = AD.ADImage.length;
  }
  for(var i=0;i<count;i++){
	  str += "<li><a href="+AD.ADImage[i].imgADLinkUrl+" onClick='adClick(\""+AD.ADImage[i].imgID+"\",\""+AD.ADImage[i].imgADLinkUrl+"\")' target='"+((AD.ADImage[i].imgADLinkTarget == "Old") ? "_self" : "_blank") + "'><img alt='"+AD.ADImage[i].imgADAlt+"' title='"+AD.ADImage[i].imgADAlt+"' src='"+this.UploadFilePath+AD.ADImage[i].ImgPath+"' ";
	  var sizeStr = "";
	  if(this.Width==0&&this.Height>0){
		  sizeStr = " height='"+this.Height+"' ";
	  }else if(this.Width>0&&this.Height==0){
		  sizeStr = " width='"+this.Width+"' ";
	  }else{
		  sizeStr = (this.Width < this.Height) ? " width='"+this.Width+"' " : " height='"+this.Height+"' ";
	  }
	  str += sizeStr;
	  str += " style='border:0px;'/></a></li>";
	}
  str += "</div>";
  document.write(str);
}
