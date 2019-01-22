

//動画情報ロード

 
 // 2. This code loads the IFrame Player API code asynchronously.
      var tag = document.createElement('script');

      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName('script')[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

      // 3. This function creates an <iframe> (and YouTube player)
      //    after the API code downloads.
      var player;
      function loadPlayer(v_Id) {
        player = new YT.Player('player', {
          height: '100%',
          width: '100%',
	videoId:v_Id,
	  playerVars:{
          rel:0,
          controls:0,
          showinfo:0
	  },
          events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
          }
        });
      }

      // 4. The API will call this function when the video player is ready.
      var timer,playerProgress;
      playerProgress = function(){
        clearTimeout(timer);
        if(event.data == YT.PlayerState.PLAYING && !done){
          timer = player.getCurrentTime();
          alert('youtubeCurrent'+timer);
        }
      };
      function onPlayerReady(event) {
        alert('youtubeReady');
        event.target.setPlaybackQuality('hd720');
        getVideoDuration();
        playerProgress();
      }

      // 5. The API calls this function when the player's state changes.
      //    The function indicates that when playing a video (state=1),
      //    the player should play for six seconds and then stop.
      var done = false;
      var setTime;    // ADD
      function onPlayerStateChange(event) {
        var ytStatus = event.target.getPlayerState();
        if (setTime) clearInterval(setTime);    // ADD
        switch (ytStatus) {
          case YT.PlayerState.PLAYING:
            setTime = setInterval(function() {
              var currentTime = Math.floor(player.getCurrentTime());
//              secret
//                if (document.querySelector('.videoAdUiSkipButtonExperimentalText')) document.querySelector('.videoAdUiSkipButtonExperimentalText').click();
              alert('Current'+currentTime);
            }, 1000);
            alert('youtubeStart');
            break;

          case YT.PlayerState.PAUSED:
            clearInterval(setTime);
            alert('youtubeStop');
            break;
            case YT.PlayerState.ENDED:
            alert('Ended');
        }
      }
      function stopVideo() {
        player.stopVideo();
      }
      function getVideoDuration(){
        alert('Duration'+player.getDuration());
      }
      function setPlayQuality(s){
        player.setPlaybackQuality(s);
      }
      function getPlayQuality(){
        alert('Quality'+player.getPlaybackQuality());
      }
      function getPlayQualityAvailable(){
        alert('Quality'+player.getAvailableQualityLevels());
      }