var Emotion = {
	setContentEmotion : function(owner) {
		var emotioner = $(owner).parents("div").eq(0);
		var txtContent = document.getElementById("txt"
				+ emotioner.attr("emotioner") + "Content");

		txtContent.innerHTML += "<span contenteditable=\"false\"><img alt=\"\" src=\"../include/images/emoticons/"
				+ owner.emotion + "\" contenteditable=\"false\" /></span>";
		txtContent.scrollTop = txtContent.scrollHeight;
		txtContent.focus();

		var imgCount = txtContent.getElementsByTagName("img").length;

		if (imgCount > 1) {
			imgCount *= 3;
			var r = document.selection.createRange();
			r.moveStart('character', txtContent.innerText.length + imgCount);

			r.collapse(true);
			r.select();
		}

		event.returnValue = false;
		emotioner.css("display", "none");
	}
}