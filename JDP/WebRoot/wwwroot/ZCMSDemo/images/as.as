btnplay._accProps = new Object();  
btnplay._accProps.name = "Play (Lecture)";  
btnplay._accProps.description = " 正在播放";  
btnplay._accProps.shortcut = "P";  
btnpause._accProps = new Object();  
btnpause._accProps.name = "Pause";  
btnpause._accProps.description = " 暂停";  
btnstop._accProps = new Object();  
btnstop._accProps.name = "Stop";  
btnstop._accProps.description = "停止";  
btnstop._accProps.shortcut = "S";  
Clip92._accProps = new Object();  
Clip92._accProps.silent = true;  
System.useCodepage = false;  
function get_mp3(str)  
{  
  //str=str.substr(0,str.length - 4) + ".mp3";  
  return str;  
}  
function init()  
{  
    if (randomplay && size > 1)  
    {  
        _root.current = Math.round(Math.random() * _root.size - 1);  
    }  
    else  
    {  
        _root.current = 0;  
    } // end else if  
    if (size < 2 || size == undefined)  
    {  
        btnprev._visible = false;  
        btnnext._visible = false;  
    } // end if  
    if (size == undefined)  
    {  
        btnplay._visible = false;  
        _root.descr = "不是 mp3 文件";  
    } // end if  
    if (autostart == 1 || autostart == "true" || autostart == "yes" || autoplay == 1 || autoplay == "true" || autoplay == "yes")  
    {  
        doload();  
    } // end if  
} // End of the function  
function pointerupdate()  
{  
    if (_root.isplaying)  
    {  
        if (_root.zone._xmouse > 0 && _root.zone._xmouse < _root.zone._width && _root.zone._ymouse > 0 && _root.zone._ymouse < _root.zone._height)  
        {  
            this._visible = true;  
            this._x = _root.track._xmouse;  
        }  
        else  
        {  
            this._visible = false;  
        } // end else if  
    }  
    else  
    {  
        this._visible = false;  
    } // end else if  
} // End of the function  
function s2time(d, z)  
{  
    if (d > 60)  
    {  
        s = d % 60;  
        if (s < 10)  
        {  
            s = "0" + s;  
        } // end if  
        d = (d - d % 60) / 60 + ":" + s;  
    }  
    else  
    {  
        if (d < 10)  
        {  
            d = "0" + d;  
        } // end if  
        if (z > 600)  
        {  
            d = "00:" + d;  
        }  
        else if (z > 60)  
        {  
            d = "0:" + d;  
        } // end else if  
    } // end else if  
    return (d);  
} // End of the function  
function updateseeker()  
{  
/*等待状态？*/
    if (_root.dewsound[_root.maindewsound].getBytesLoaded() < _root.dewsound[_root.maindewsound].getBytesTotal() && _root.isplaying)
    {
        _root.buffering._visible = true;
    }
    else
    {
        _root.buffering._visible = false;
    } // end if
    if (_root.track.seeker._alpha < 100)
    {
        _root.track.seeker._alpha = _root.track.seeker._alpha + 10;
    } // end if

/**/
    if (_root.durations[current] != undefined)  
    {  
        _root.sposition = _root.dewsound[_root.maindewsound].position / _root.durations[current] * 100;  
        d = Math.round(_root.durations[current] / 1000);  
    }  
    else  
    {  
        _root.sposition = _root.dewsound[_root.maindewsound].position / _root.dewsound[_root.maindewsound].duration * 100;  
        d = Math.round(_root.dewsound[_root.maindewsound].duration / 1000);  
    } // end else if  
    p = Math.round(_root.dewsound[_root.maindewsound].position / 1000);  
    if (_root.track.seeker._alpha < 100)  
    {  
        _root.track.seeker._alpha = _root.track.seeker._alpha + 10;  
    } // end if  
    pos = Math.round(_root.sposition * 5);  
    if (pos > 0)  
    {  
        _root.track.gotoAndStop(pos);  
    } // end if  
    if (_root.showtime || _root.id3)  
    {  
        _root.positiontime = s2time(p, d);  
        _root.totaltime = s2time(d);  
    }  
    else  
    {  
        _root.positiontime = p;  
        _root.totaltime = d;  
    } // end else if  
    if (_root.id3 && _root.showtimedelay <= 0)  
    {  
        _root.descr = " " + _root.id3 + " (" + _root.positiontime + " | " + _root.totaltime + ")                                            ";  
        _root.descrfield.hscroll = _root.descrfield.hscroll + scrolldir;  
        if (_root.descrfield.hscroll == _root.descrfield.maxhscroll)  
        {  
            _root.descrfield.hscroll = 0;  
        } // end if  
    }  
    else  
    {  
        _root.descrfield.hscroll = 0;  
        _root.descr = _root.positiontime + " | " + _root.totaltime;  
        --_root.showtimedelay;  
    } // end else if  
} // End of the function  
function fadeseeker()  
{  
    if (_root.track.seeker._alpha > 0)  
    {  
        _root.track.seeker._alpha = _root.track.seeker._alpha - 5;  
    }  
    else  
    {  
        clearInterval(fadeseeker_interval);  
    } // end else if  
} // End of the function  
function donext()  
{  
    _root.currentprev = _root.current;  
    if (randomplay > 0)  
    {  
        current = Math.round(Math.random() * _root.size - 1);  
        if (current < 0)  
        {  
            current = 0;  
        } // end if  
    }  
    else  
    {  
        ++current;  
        if (current > size - 1)  
        {  
            current = 0;  
            if (_root.playlist)  
            {  
                _root.playlist.scroll.scroll_cursor._y = _root.scroll_top_limit;  
                scroll_tracks();  
            } // end if  
        } // end if  
    } // end else if  
    doload();  
} // End of the function  
function doprev()  
{  
    _root.currentprev = _root.current;  
    --current;  
    if (current < 0)  
    {  
        current = size - 1;  
    } // end if  
    doload();  
} // End of the function  
function getid3()  
{  
    songname = _root.dewsound[_root.maindewsound].id3.songname;  
    artist = _root.dewsound[_root.maindewsound].id3.artist;  
    if (songname != "undefined" && songname != undefined && songname.length > 0 && artist != "undefined" && artist != undefined && artiste.length > 0)  
    {  
        _root.id3 = artist + " - " + songname;  
    }  
    else if (songname != "undefined" && songname != undefined && songname.length > 0)  
    {  
        _root.id3 = artist;  
    } // end else if  
} // End of the function  
function doload()  
{  
    if (_root.current < 0)  
    {  
        current = 0;  
    } // end if  
    _root.id3 = false;  
    _root.maindewsound = current;  
    if (fade != undefined && fade > 0)  
    {  
        fadeout(_root.dewsound[currentprev]);  
    }  
    else  
    {  
        stopAllSounds ();  
    } // end else if  
    if (_root.cover != undefined)  
    {  
        show_cover();  
    } // end if  
    if (_root.ticker != undefined)  
    {  
        ticker_update();  
    } // end if  
    if (_global.Behaviors == null)  
    {  
        _global.Behaviors = {};  
    } // end if  
    if (_global.Behaviors.Sound == null)  
    {  
        _global.Behaviors.Sound = {};  
    } // end if  
    if (_root.dewsound[_root.maindewsound] == undefined)  
    {  
        if (typeof(this.createEmptyMovieClip) == "undefined")  
        {  
            this._parent.createEmptyMovieClip("BS_dewsound1", new Date().getTime() - Math.floor(new Date().getTime() / 10000) * 10000);  
            _root.dewsound[_root.maindewsound] = new Sound(this._parent.BS_dewsound1);  
        }  
        else  
        {  
            this.createEmptyMovieClip("BS_dewsound1", new Date().getTime() - Math.floor(new Date().getTime() / 10000) * 10000);  
            _root.dewsound[_root.maindewsound] = new Sound(this.BS_dewsound1);  
        } // end if  
    } // end else if  
    if (volume > 0)  
    {  
        _root.dewsound[_root.maindewsound].setVolume(volume);  
    } // end if  
    _root.dewsound[_root.maindewsound].onSoundComplete = function ()  
    {  
        if (size > 1 && (autoreplay > 0 || autoreplay == "true" || autoreplay == "yes"))  
        {  
            donext();  
        }  
        else if (autoreplay > 0 || autoreplay == "true" || autoreplay == "yes")  
        {  
            _root.isplaying = false;  
            _root.sposition = 0;  
            doplay();  
        }  
        else  
        {  
            dostop();  
        } // end else if  
    };  
    _root.dewsound[_root.maindewsound].onID3 = getid3;  
    _root.dewsound[_root.maindewsound].loadSound(get_mp3(_root.mp3[current]), true);  
    _root.dewsound[_root.maindewsound].start();  
    if (fade != undefined && fade != 0)  
    {  
        fadein(_root.dewsound[_root.maindewsound]);  
    } // end if  
    _root.loaded = true;  
    doplay();  
} // End of the function  
function doplay()  
{  
    btnplay._visible = false;  
    btnpause._visible = true;  
    btnstop._visible = true;  
    if (_root.current == -1)  
    {  
        donext();  
        return;  
    } // end if  
    if (!_root.loaded)  
    {  
        doload();  
    } // end if  
    if (_root.durations[current] != undefined)  
    {  
        position = _root.sposition * (_root.durations[current] / 100000);  
    }  
    else  
    {  
        position = _root.sposition * (_root.dewsound[_root.maindewsound].duration / 100000);  
    } // end else if  
    if (!_root.isplaying)  
    {  
        _root.dewsound[_root.maindewsound].start(position, 1);  
    } // end if  
    _root.isplaying = true;  
    clearInterval(fadeseeker_interval);  
    clearInterval(updateseeker_interval);  
    updateseeker_interval = setInterval(updateseeker, interval);  
    if (!dontmaskhelp)  
    {  
        _root.btnhelp._visible = false;  
    } // end if  
    if (_root.playlist)  
    {  
        playlist_highlight(_root.current, "play");  
    } // end if  
} // End of the function  
function dopause()  
{  
    btnplay._visible = true;  
    btnpause._visible = false;  
    if (_root.isplaying)  
    {  
        btnstop._visible = true;  
    }  
    else  
    {  
        btnstop._visible = false;  
    } // end else if  
    _root.sposition = _root.dewsound[_root.maindewsound].position / 1000;  
    _root.dewsound[_root.maindewsound].stop();  
    _root.isplaying = false;  
} // End of the function  
function dostop()  
{  
    btnplay._visible = true;  
    btnpause._visible = false;  
    btnstop._visible = false;  
    _root.dewsound[_root.maindewsound].stop();  
    clearInterval(updateseeker_interval);  
    _root.sposition = 0;  
    _root.descr = "";  
    _root.track.gotoAndStop(1);  
    _root.isplaying = false;  
} // End of the function  
function dogo(track)  
{  
    if (track != undefined)  
    {  
        _root.currentprev = _root.current;  
        _root.current = track - 1;  
        doload();  
    } // end if  
    doplay();  
} // End of the function  
function doset(mp3input)  
{  
    if (mp3input != undefined)  
    {  
        _root.mp3.push(mp3input);  
        _root.size = _root.mp3.length;  
        _root.current = _root.size - 1;  
        doload();  
    } // end if  
} // End of the function  
function dosetpos(newpos)  
{  
    if (_root.dewsound[_root.maindewsound] != undefined)  
    {  
        _root.dewsound[_root.maindewsound].start(newpos / 1000, 1);  
    } // end if  
} // End of the function  
function dogetpos()  
{  
    if (_root.dewsound[_root.maindewsound] != undefined && isplaying)  
    {  
        return (_root.dewsound[_root.maindewsound].position);  
    }  
    else  
    {  
        return (0);  
    } // end else if  
} // End of the function  
function playlist_loaded(rulez)  
{  
    if (rulez)  
    {  
        var _loc6 = this.firstChild;  
        for (var _loc4 = _loc6.firstChild; _loc4 != null; _loc4 = _loc4.nextSibling)  
        {  
            if (_loc4.nodeName == "title")  
            {  
                playlist_title = _loc4.firstChild.nodeValue;  
            } // end if  
            if (_loc4.nodeName == "trackList")  
            {  
                _root.mp3 = new Array();  
                _root.titles = new Array();  
                _root.images = new Array();  
                _root.links = new Array();  
                _root.creators = new Array();  
                _root.infos = new Array();  
                _root.albums = new Array();  
                i = 0;  
                for (var _loc5 = _loc4.firstChild; _loc5 != null; _loc5 = _loc5.nextSibling)  
                {  
                    for (var _loc3 = _loc5.firstChild; _loc3 != null; _loc3 = _loc3.nextSibling)  
                    {  
                        if (_loc3.nodeName == "location")  
                        {  
                            _root.mp3[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "image")  
                        {  
                            _root.images[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "title")  
                        {  
                            _root.titles[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "creator")  
                        {  
                            _root.creators[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "album")  
                        {  
                            _root.albums[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "info")  
                        {  
                            _root.infos[i] = _loc3.firstChild.nodeValue;  
                            continue;  
                        } // end if  
                        if (_loc3.nodeName == "link")  
                        {  
                            _root.links[i] = _loc3.firstChild.nodeValue;  
                        } // end if  
                    } // end of for  
                    ++i;  
                } // end of for  
                continue;  
            } // end if  
            if (_loc4.nodeName == "bgcolor")  
            {  
                _root.bgcolor=_loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "autostart")  
            {  
                _root.autostart=_loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "autoreplay")  
            {  
                _root.autoreplay=_loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "showtime")  
            {  
               _root.showtime=_loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "randomplay")  
            {  
                    _root.randomplay=_loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "image")  
            {  
                if (_root.cover != undefined)  
                {  
                    _root.cover.loadMovie(_loc4.firstChild.nodeValue,"cover_img");   
                } // end if  
                continue;  
            } // end if  
            if (_loc4.nodeName == "creator")  
            {  
                _root.creator = _loc4.firstChild.nodeValue;  
                continue;  
            } // end if  
            if (_loc4.nodeName == "link")  
            {  
                if (btncover != undefined)  
                {  
                    _root.link = _loc4.firstChild.nodeValue;  
                    btncover.onRelease = function ()  
                    {  
                        getURL(link, "_blank");  
                    };  
                    btncover._visible = true;  
                } // end if  
            } // end if  
        } // end of for  
        _root.size = _root.mp3.length;  
        if (_root.playlist != undefined && size > 0)  
        {  
            if (_root.playlist)  
            {  
                playlist_init();  
            } // end if  
            init();  
        } // end if  
    }  
    else  
    {  
        _root.descr = xml+" error";  
        //getURL("默认pl.xml;或flashvar=xmltype=(asp|xml|php)或 mp3=xx.mp3|yy.mp3","_blank");  
    } // end else if  
} // End of the function  
function playlist_init()  
{  
    for (i = 0; i < _root.size; i++)  
    {  
        if (titles[i] != undefined)  
        {  
            playlist_add(i, (i+1) + ". " + titles[i]);  
            continue;  
        } // end if  
        playlist_add(i, mp3[i]);  
    } // end of for  
    _root.scroll_top_limit = _root.playlist.scroll.scroll_cursor._y;  
    _root.scroll_bottom_limit = _root.playlist.scroll._height - _root.playlist.scroll.scroll_cursor._height - 1;  
    _root.scroll_decal = _root.playlist.scroll.scroll_cursor._y - _root.playlist.scroll._y;  
    _root.scroll_tracks_start = _root.playlist.list.listfond._y;  
    _root.playlist.scroll.scroll_cursor.drag_btn.onPress = function ()  
    {  
        this._parent.startDrag(false, this._parent._x, _root.scroll_top_limit, this._parent._x, _root.scroll_bottom_limit);  
        this._parent.isdragging = true;  
        this._parent.onEnterFrame = scroll_tracks;  
    };  
    _root.playlist.scroll.scroll_cursor.drag_btn.onRelease = _root.playlist.scroll.scroll_cursor.drag_btn.onReleaseOutside = function ()  
    {  
        stopDrag ();  
        this._parent.isdragging = false;  
        this._parent.onEnterFrame = null;  
    };  
    var _loc3 = new Object();  
    _loc3.onMouseWheel = function (wheelNum)  
    {  
        if (wheelNum > 0)  
        {  
            _root.playlist.scroll.scroll_cursor._y = Math.max(_root.scroll_top_limit, _root.playlist.scroll.scroll_cursor._y - 5);  
            scroll_tracks();  
        }  
        else if (wheelNum < 0)  
        {  
            _root.playlist.scroll.scroll_cursor._y = Math.min(_root.scroll_bottom_limit, _root.playlist.scroll.scroll_cursor._y + 5);  
            scroll_tracks();  
        } // end else if  
    };  
    Mouse.addListener(_loc3);  
} // End of the function  
function doadd(title,mp3){
    _root.titles.push(title);
    _root.mp3.push(mp3);
    _root.size = _root.mp3.length;
	i=_root.size;
    playlist_add(i-1, i + ". " + title);  
}
function playlist_add(index, title)  
{  
    _root.playlist.list.tracks.track.duplicateMovieClip("track_" + index, index + 10);  
    _root.playlist.list.tracks["track_" + index]._y = index * (_root.playlist.list.tracks.track._height + 2);  
    _root.playlist.list.tracks["track_" + index]._x = 0;  
    _root.playlist.list.tracks["track_" + index].onRelease = function ()  
    {  
        dogo(index + 1);  
        playlist_highlight(index, "play");  
    };  
    _root.playlist.list.tracks["track_" + index].onRollOver = function ()  
    {  
        playlist_highlight(index, "over");  
    };  
    _root.playlist.list.tracks["track_" + index].onRollOut = function ()  
    {  
        playlist_highlight(index, "out");  
    };  
    _root.playlist.list.tracks["track_" + index].trackname.text = title;  
} // End of the function  
function playlist_highlight(index, status)  
{  
    if (index == _root.current && (status == "over" || status == "out"))  
    {  
    }  
    else if (status == "play")  
    {  
        for (i = 0; i < _root.size; i++)  
        {  
            _root.playlist.list.tracks["track_" + i].bgplaying._alpha = 0;  
        } // end of for  
        _root.playlist.list.tracks["track_" + index]._alpha = 100;  
        _root.playlist.list.tracks["track_" + index].bgplaying._alpha = 100;  
    }  
    else if (status == "over")  
    {  
        _root.playlist.list.tracks["track_" + index]._alpha = 50;  
    }  
    else if (status == "out")  
    {  
        _root.playlist.list.tracks["track_" + index]._alpha = 100;  
    } // end else if  
} // End of the function  
function scroll_tracks()  
{  
    var _loc2 = Math.floor(_root.playlist.scroll.scroll_cursor._y - _root.playlist.scroll._y - _root.scroll_decal) / (_root.playlist.scroll._height - _root.playlist.scroll.scroll_cursor._height - _root.scroll_decal) * 100;  
    _loc2 = Math.min(_loc2, 100);  
    _root.playlist.list.tracks._y = _root.scroll_tracks_start - _loc2 / 100 * (_root.playlist.list.tracks._height - _root.playlist.list.listfond._height);  
} // End of the function  
function show_cover()  
{  
    loadMovie(images[_root.current], _root.cover);  
    if (links[_root.current] != undefined)  
    {  
        btncover.onRelease = function ()  
        {  
            getURL(links[_root.current], "_blank");  
        };  
        btncover._visible = true;  
    }  
    else  
    {  
        btncover._visible = false;  
    } // end else if  
} // End of the function  
function ticker_update()  
{  
    ticker._alpha = 0;  
    if (creators[current] != undefined)  
    {  
        ticker.creator.text = creators[current];  
    } // end if  
    if (albums[current] != undefined)  
    {  
        ticker.album.text = albums[current];  
    } // end if  
    if (infos[current] != undefined)  
    {  
        ticker.info.text = infos[current];  
    }  
    else  
    {  
        ticker.info.text = titles[current];  
    } // end else if  
    ticker_show();  
} // End of the function  
function ticker_show()  
{  
    clearInterval(tickerfade_interval);  
    clearInterval(tickerhide_interval);  
    _root.ticker_fading = 1;  
    tickerfade_interval = setInterval(ticker_fade, 5);  
    tickerhide_interval = setInterval(ticker_hide, ticker_delay);  
} // End of the function  
function ticker_hide()  
{  
    if (cover_over == false)  
    {  
        clearInterval(tickerfade_interval);  
        _root.ticker_fading = -1;  
        tickerfade_interval = setInterval(ticker_fade, 5);  
    } // end if  
} // End of the function  
function ticker_fade()  
{  
    ticker._alpha = ticker._alpha + _root.ticker_fading;  
    if (ticker._alpha >= 80 || ticker._alpha <= 0)  
    {  
        clearInterval(tickerfade_interval);  
    } // end if  
    if (ticker._alpha <= 0)  
    {  
        clearInterval(tickerhide_interval);  
    } // end if  
    if (ticker._alpha > 30)  
    {  
        ticker.creator._visible = ticker.album._visible = ticker.info._visible = true;  
    }  
    else  
    {  
        ticker.creator._visible = ticker.album._visible = ticker.info._visible = false;  
    } // end else if  
} // End of the function  
function updatevolume()  
{  
    setvol(this._xmouse / this._parent._width * 100);  
    this._parent.onMouseMove = this._parent.onMouseDown = function ()  
    {  
        setvol(this._xmouse / this._width * 100);  
    };  
} // End of the function  
function setvol(percent)  
{  
    if (percent > 100)  
    {  
        percent = 100;  
    } // end if  
    if (percent < 0)  
    {  
        percent = 0;  
    } // end if  
    if (_root.btnvolume != undefined)  
    {  
        btnvolume.volume_bar._xscale = percent;  
    } // end if  
    _root.volume = percent;  
    _root.currentdewsound.setVolume(_root.volume);  
} // End of the function  
version = "1.9.8";  
if (!scale)  
{  
    Stage.align = "TL";  
    Stage.scaleMode = "noScale";  
} // end if  
if (flash.external.ExternalInterface.available && javascript != undefined && javascript != "off")  
{  
    flash.external.ExternalInterface.addCallback("callplay", null, doplay);  
    flash.external.ExternalInterface.addCallback("callstop", null, dostop);  
    flash.external.ExternalInterface.addCallback("callpause", null, dopause);  
    flash.external.ExternalInterface.addCallback("callnext", null, donext);  
    flash.external.ExternalInterface.addCallback("callprev", null, doprev);  
    flash.external.ExternalInterface.addCallback("callgo", null, dogo);  
    flash.external.ExternalInterface.addCallback("callset", null, doset);  
    flash.external.ExternalInterface.addCallback("callsetpos", null, dosetpos);  
    flash.external.ExternalInterface.addCallback("callgetpos", null, dogetpos);  
    flash.external.ExternalInterface.addCallback("calladd", null, doadd);  
} // end if  
about = function ()  
{  
    getURL("http://www.alsacreations.fr/dewplayer",'_blank');  
};  
menu = new ContextMenu();  
menu.hideBuiltInItems();  
commandeAbout = new ContextMenuItem("Dewplayer " + version + " by Alsacreations edit by qidizi", about);  
menu.customItems.push(commandeAbout);  
btnhelp.onRelease = about;  
var fadeseeker_interval;  
var updateseeker_interval;  
var sposition = 0;  
var interval = 50;  
var isplaying = false;  
var loaded = false;  
var scrolldir = 1;  
var showtimedelay = 0;  
var size = 0;  
var current = -1;  
var currentprev = -2;  
var dewsound = new Array();  
var maindewsound = 0;  
volume = 100;  
btnplay._visible = true;  
btnpause._visible = false;  
btnstop._visible = false;  
btnplay.onRelease = doplay;  
btnpause.onRelease = dopause;  
btnstop.onRelease = dostop;  
btnnext.onRelease = donext;  
btnprev.onRelease = doprev;  
  
track.onPress = function ()  
{  
    if (_root.isplaying)  
    {  
        decal = (_root._xmouse - _root.zone._x) / _root.zone._width;  
        if (_root.durations[current] != undefined)  
        {  
            decal = _root.durations[current] * decal / 1000;  
        }  
        else  
        {  
            decal = _root.dewsound[_root.maindewsound].duration * decal / 1000;  
        } // end else if  
        if (decal > 0)  
        {  
            _root.dewsound[_root.maindewsound].start(decal, 1);  
        } // end if  
        _root.showtimedelay = 30;  
    } // end if  
};  
if (_root.nopointer)  
{  
    _root.track.pointer._visible = false;  
}  
else  
{  
    _root.track.pointer.onEnterFrame = pointerupdate;  
} // end else if  
if (xml == undefined && mp3 == undefined)
{
	_root.mp3 = new Array();
	_root.titles=new Array();
	_root.size = _root.mp3.length;
	//getURL("javascript:alert('"+_root.size+"')");
	playlist_init();  
	init();
}else if (mp3 != undefined)
{
	if(mp3 != ""){
	    _root.mp3 = mp3.split("|");
	    _root.titles=titles.split("|");
    }else{
	    _root.mp3 = new Array();
	    _root.titles=new Array();
    }
    _root.size = _root.mp3.length;
    //getURL("javascript:alert('"+_root.size+"')");
    playlist_init();  
    init();
}else if (xml != undefined)  
{  
	/**
    pl_path =_url.substr (0,_url.lastIndexOf ("/")+1);  
    xml = pl_path+"playlist.xml";
    trace(xml)
    if (_root.vars != undefined)  
    {  
      xml += "?" + _root.vars;  
    }  
	**/      
    playlist_xml = new XML();  
    playlist_xml.ignoreWhite = true;  
    playlist_xml.onLoad = playlist_loaded;  
    playlist_xml.load(unescape(xml));  
} // end if  
if (_root.btncover != undefined)  
{  
    _root.cover_over = false;  
    btncover._visible = false;  
    btncover.onRollOver = function ()  
    {  
        if (_root.current > -1)  
        {  
            _root.cover_over = true;  
            ticker_show();  
        } // end if  
    };  
    btncover.onRollOut = function ()  
    {  
        _root.cover_over = false;  
        ticker_hide();  
    };  
} // end if  
if (_root.ticker != undefined)  
{  
    _root.ticker._alpha = 0;  
    _root.ticker.onRollOver = function ()  
    {  
        this._alpha = 100;  
    };  
    _root.ticker.onRollOut = function ()  
    {  
        this._alpha = 80;  
    };  
} // end if  
flash.external.ExternalInterface.addCallback("dewvolume", null, setvol);  
  
if (_root.volume > -1 && _root.volume < 101)  
{  
    btnvolume.volume_bar._xscale = _root.volume;  
} // end if  
btnvolume.volume_btn.onPress = updatevolume;  
btnvolume.volume_btn.onRelease = btnvolume.volume_btn.onReleaseOutside = function ()  
{  
    this._parent.onMouseMove = this._parent.onMouseDown = null;  
};  
  
_level0.playlist.list.setMask(_level0.playlist.list.zhezhao);  
stop();  