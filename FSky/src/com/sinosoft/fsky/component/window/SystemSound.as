package com.sinosoft.fsky.component.window
{
	import mx.core.SoundAsset;

	public class SystemSound
	{
		[Embed('assets/sound/ding.mp3')] 
		private static var dingSound:Class; 
		public static const DING:SoundAsset = new dingSound() as SoundAsset;
		
		public function SystemSound()
		{
		}
	}
}