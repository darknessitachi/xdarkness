 package com.zving.bbs.admin;
 
 import com.zving.bbs.ForumCache;
 import com.zving.bbs.ForumUtil;
 import com.zving.schema.ZCForumScoreSchema;
 
 public class ForumScore
 {
   public long InitScore;
   public long PublishTheme;
   public long DeleteTheme;
   public long PublishPost;
   public long DeletePost;
   public long Best;
   public long CancelBest;
   public long Bright;
   public long CancelBright;
   public long TopTheme;
   public long CancelTop;
   public long UpTheme;
   public long DownTheme;
   public long Upload;
   public long Download;
   public long Search;
   public long Vote;
 
   public ForumScore()
   {
     init();
   }
 
   public ForumScore(String SiteID) {
     this(Long.parseLong(SiteID));
   }
 
   public ForumScore(long SiteID) {
     init(SiteID);
   }
 
   public void init(long SiteID) {
     if (!ForumUtil.isInitDB(SiteID)) {
       return;
     }
     ZCForumScoreSchema score = ForumCache.getScoreBySiteID(String.valueOf(SiteID));
     if (score != null) {
       this.InitScore = score.getInitScore();
       this.PublishTheme = score.getPublishTheme();
       this.DeleteTheme = score.getDeleteTheme();
       this.PublishPost = score.getPublishPost();
       this.DeletePost = score.getDeletePost();
       this.Best = score.getBest();
       this.CancelBest = score.getCancelBest();
       this.Bright = score.getBright();
       this.CancelBright = score.getCancelBright();
       this.TopTheme = score.getTopTheme();
       this.CancelTop = score.getCancelTop();
       this.UpTheme = score.getUpTheme();
       this.DownTheme = score.getDownTheme();
       this.Upload = score.getUpload();
       this.Download = score.getDownload();
       this.Search = score.getSearch();
       this.Vote = score.getVote();
     } else {
       clean();
     }
   }
 
   public void init() {
     init(ForumUtil.getCurrentBBSSiteID());
   }
 
   public void clean() {
     this.InitScore = 0L;
     this.PublishTheme = 0L;
     this.DeleteTheme = 0L;
     this.PublishPost = 0L;
     this.DeletePost = 0L;
     this.Best = 0L;
     this.CancelBest = 0L;
     this.Bright = 0L;
     this.CancelBright = 0L;
     this.TopTheme = 0L;
     this.CancelTop = 0L;
     this.UpTheme = 0L;
     this.DownTheme = 0L;
     this.Upload = 0L;
     this.Download = 0L;
     this.Search = 0L;
     this.Vote = 0L;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.admin.ForumScore
 * JD-Core Version:    0.5.4
 */