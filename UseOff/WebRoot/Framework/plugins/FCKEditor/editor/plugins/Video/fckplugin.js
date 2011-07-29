var VideoCommond = function() {this.Name = 'Video';}
VideoCommond.prototype.Execute = function () {Video();}
VideoCommond.prototype.GetState = function() {return FCK_TRISTATE_OFF;}

// Register the related command.
FCKCommands.RegisterCommand( 'Video',new VideoCommond()) ;

// Create the "Plaholder" toolbar button.
var VideoItem = new FCKToolbarButton( 'Video', "视频上传" ) ;
VideoItem.IconPath = FCKPlugins.Items['Video'].Path + 'Video.gif' ;
FCKToolbarItems.RegisterItem( 'Video', VideoItem ) ;

function Video(){
	FCKUndo.SaveUndoStep() ;
	//FCK.SetHTML(formattext(FCK.GetXHTML(false))); 
	if(window.parent.upload){
		window.parent.uploadVideo();
	}else if(window.parent.parent){
		window.parent.parent.uploadVideo();
	}
	FCKUndo.SaveUndoStep() ;
}
