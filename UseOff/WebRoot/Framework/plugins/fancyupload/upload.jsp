<%@ page language="java"  pageEncoding="gbk" contentType="text/html;charset=gbk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">

<script type="text/javascript" src="<%=request.getContextPath()%>/Framework/src/core/Browser.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Framework/src/core/ElementCore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Framework/src/core/ElementExt.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Framework/src/core/Page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Framework/dialog.js"></script>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>文件上传</title>

	<script type="text/javascript" src="source/mootools.js"></script>

	<script type="text/javascript" src="source/Swiff.Uploader.js"></script>

	<script type="text/javascript" src="source/Fx.ProgressBar.js"></script>

	<script type="text/javascript" src="source/Lang.js"></script>

	<script type="text/javascript" src="source/FancyUpload2.js"></script>


	<!-- See script.js -->
	<script type="text/javascript">
		//<![CDATA[

var UploadManager = null;

window.addEvent('domready', function() { // wait for the content

	// our uploader instance 
	
	UploadManager = up = new FancyUpload2($('demo-status'), $('sky_upload_file_list'), { // options object
		// we console.log infos, remove that in production!!
		verbose: true,
		
		// url is read from the form, so you just have to change one place
		url: '<%=request.getParameter("url")%>',
		data: 'moduleCode=<%=request.getParameter("moduleCode")%>&bizId=<%=request.getParameter("bizId")%>',
				
		// path to the SWF file
		path: 'source/Swiff.Uploader.swf',
		
		// remove that line to select all files, or edit it, add more items
		typeFilter: {
			'文件(<%=request.getParameter("typeFilter")%>)':'<%=request.getParameter("typeFilter")%>'
		},
		queued: <%=request.getParameter("queued")%>,
		multiple: <%=request.getParameter("multiple")%>,
		limitSize: <%=request.getParameter("limitSize")%>,
		limitFiles: <%=request.getParameter("limitFiles")%>,
		instantStart: <%=request.getParameter("instantStart")%>,
		allowDuplicates: <%=request.getParameter("allowDuplicates")%>,
		      
		// this is our browse button, *target* is overlayed with the Flash movie
		target: 'demo-browse',
		
		// graceful degradation, onLoad is only called if all went well with Flash
		onLoad: function() {
			$('demo-status').removeClass('hide'); // we show the actual UI
			//$('demo-fallback').destroy(); // ... and hide the plain form
			
			// We relay the interactions with the overlayed flash to the link
			this.target.addEvents({
				click: function() {
					return false;
				},
				mouseenter: function() {
					this.addClass('hover');
				},
				mouseleave: function() {
					this.removeClass('hover');
					this.blur();
				},
				mousedown: function() {
					this.focus();
				}
			});

			// Interactions for the 2 other buttons
			
			$('demo-clear').addEvent('click', function() {
				up.remove(); // remove all files
				return false;
			});

			$('demo-upload').addEvent('click', function() {
				up.start(); // start upload
				return false;
			});
		},
		
		// Edit the following lines, it is your custom event handling
		
		/**
		 * Is called when files were not added, "files" is an array of invalid File classes.
		 * 
		 * This example creates a list of error elements directly in the file list, which
		 * hide on click.
		 */ 
		onSelectFail: function(files) {
			files.each(function(file) {
				new Element('li', {
					'class': 'validation-error',
					html: file.validationErrorMessage || file.validationError,
					title: MooTools.lang.get('FancyUpload', 'removeTitle'),
					events: {
						click: function() {
							this.destroy();
						}
					}
				}).inject(this.list, 'top');
			}, this);
		},
		
		/**
		 * This one was directly in FancyUpload2 before, the event makes it
		 * easier for you, to add your own response handling (you probably want
		 * to send something else than JSON or different items).
		 */
		onFileSuccess: function(file, response) {
			
			var json = new Hash(JSON.decode(response) || {});
			
			if (json.get('success') == true) {
				file.element.addClass('file-success');
				file.fileCode = json.get('fileCode');
				file.info.set('html', '<strong>上传成功</em>');
			} else {
				file.element.addClass('file-failed');
				file.info.set('html', '<strong>发生错误:</strong> ' + (json.get('error') ? (json.get('error') + ' #' + json.get('code')) : response));
			}
		},
		
		/**
		 * onFail is called when the Flash movie got bashed by some browser plugin
		 * like Adblock or Flashblock.
		 */
		onFail: function(error) {
			switch (error) {
				case 'hidden': // works after enabling the movie and clicking refresh
					alert('To enable the embedded uploader, unblock it in your browser and refresh (see Adblock).');
					break;
				case 'blocked': // This no *full* fail, it works after the user clicks the button
					alert('To enable the embedded uploader, enable the blocked Flash movie (see Flashblock).');
					break;
				case 'empty': // Oh oh, wrong path
					alert('A required file was not found, please be patient and we fix this.');
					break;
				case 'flash': // no flash 9+ :(
					alert('To enable the embedded uploader, install the latest Adobe Flash plugin.')
			}
		}
		
	});
	
});

/* 修正flash自身销毁元素的bug */
function overrideFlashRemoveCallback() {
	window["__flash__removeCallback"] = function (instance, name) {
		try{
			if(instance) instance[name] = null;
		}catch(ex){
		}
	}
}setTimeout(overrideFlashRemoveCallback, 50);	

		//]]>
	</script>



	<!-- See style.css -->
	<style type="text/css">

/* CSS vs. Adblock tabs */
.swiff-uploader-box a {
	display: none !important;
}

/* .hover simulates the flash interactions */
a:hover, a.hover {
	color: red;
}

#demo-status {
	padding: 10px 15px;
	width: 420px;
	border: 1px solid #eee;
}

#demo-status .progress {
	background: url(assets/progress-bar/progress.gif) no-repeat;
	background-position: +50% 0;
	margin-right: 0.5em;
	vertical-align: middle;
}

#demo-status .progress-text {
	font-size: 0.9em;
	font-weight: bold;
}

#sky_upload_file_list {
	list-style: none;
	width: 450px;
	margin: 0;
}

#sky_upload_file_list li.validation-error {
	padding-left: 44px;
	display: block;
	clear: left;
	line-height: 40px;
	color: #8a1f11;
	cursor: pointer;
	border-bottom: 1px solid #fbc2c4;
	background: #fbe3e4 url(assets/failed.png) no-repeat 4px 4px;
}

#sky_upload_file_list li.file {
	border-bottom: 1px solid #eee;
	background: url(assets/file.png) no-repeat 4px 4px;
	overflow: auto;
}
#sky_upload_file_list li.file.file-uploading {
	background-image: url(assets/uploading.png);
	background-color: #D9DDE9;
}
#sky_upload_file_list li.file.file-success {
	background-image: url(assets/success.png);
}
#sky_upload_file_list li.file.file-failed {
	background-image: url(assets/failed.png);
}

#sky_upload_file_list li.file .file-name {
	font-size: 1.2em;
	margin-left: 44px;
	display: block;
	clear: left;
	line-height: 40px;
	height: 40px;
	font-weight: bold;
}
#sky_upload_file_list li.file .file-size {
	font-size: 0.9em;
	line-height: 18px;
	float: right;
	margin-top: 2px;
	margin-right: 6px;
}
#sky_upload_file_list li.file .file-info {
	display: block;
	margin-left: 44px;
	font-size: 0.9em;
	line-height: 20px;
	clear
}
#sky_upload_file_list li.file .file-remove {
	clear: right;
	float: right;
	line-height: 18px;
	margin-right: 6px;
}	</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/Framework/resources/css/default.css"
      type="text/css"></link>

</head>
<body>

	<div class="container">

		<!-- See index.html -->
		<div>
<form  method="post" enctype="multipart/form-data" id="form-demo">

	<fieldset id="demo-fallback" style="display:none">
		<legend>文件上传</legend>
		<p>
			This form is just an example fallback for the unobtrusive behaviour of FancyUpload.
			If this part is not changed, something must be wrong with your code.
		</p>
		<label for="demo-photoupload">
			上传一个文件:
			<input type="file" name="Filedata" />
		</label>
	</fieldset>

	<div id="demo-status">
		<p>
			<a href="#" id="demo-browse" hidefocus="true"><img src="<%=request.getContextPath() %>/Framework/resources/icons/icon022a2.gif"></img>增加</a> |
			<a href="#" id="demo-clear" hidefocus="true"><img src="<%=request.getContextPath() %>/Framework/resources/icons/icon022a3.gif"></img>清空</a> |
			<a href="#" id="demo-upload" hidefocus="true"><img src="<%=request.getContextPath() %>/Framework/resources/icons/icon022a7.gif"></img>上传</a>
		</p>
		<div>
			<strong class="overall-title"></strong><br />
			<img src="assets/progress-bar/bar.gif" class="progress overall-progress" />
		</div>
		<div>
			<strong class="current-title"></strong><br />
			<img src="assets/progress-bar/bar.gif" class="progress current-progress" />
		</div>
		<div class="current-text"></div>
	</div>

	<ul id="sky_upload_file_list"></ul>

</form>		</div>


	</div>

</body>
</html>
