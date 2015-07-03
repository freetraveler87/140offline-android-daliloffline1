package com.homains.daliloffline;

import java.util.ArrayList;
import java.util.List;

import com.homains.daliloffline.fav.ui.FavFragment;
import com.homains.daliloffline.maps.MapsFragment;
import com.homains.daliloffline.media.ui.MediaFragment;
import com.homains.daliloffline.rss.ui.RssFragment;
import com.homains.daliloffline.tumblr.ui.TumblrFragment;
import com.homains.daliloffline.twi.ui.TweetsFragment;
import com.homains.daliloffline.web.WebviewFragment;
import com.homains.daliloffline.wordpress.ui.WordpressFragment;
import com.homains.daliloffline.yt.ui.VideosFragment;

public class Config {
	
	public static List<NavItem> configuration() {
		
		List<NavItem> i = new ArrayList<NavItem>();
        
		//DONT MODIFY ABOVE THIS LINE
		
		i.add(new NavItem("Section", NavItem.SECTION));
        i.add(new NavItem("Uploaded Videos", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, VideosFragment.class, "UU7V6hW6xqPAiUfataAZZtWA,UC7V6hW6xqPAiUfataAZZtWA"));
        i.add(new NavItem("Liked Videos", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, VideosFragment.class, "LL7V6hW6xqPAiUfataAZZtWA"));
        
        i.add(new NavItem("News", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, RssFragment.class, "http://feeds.feedburner.com/AndroidPolice"));
        i.add(new NavItem("Tip Us", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, WebviewFragment.class, "http://www.androidpolice.com/contact/"));
        
        i.add(new NavItem("Recent Posts", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, WordpressFragment.class, "http://androidpolice.com/api/"));
        i.add(new NavItem("Cat: Conservation", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, WordpressFragment.class, "http://moma.org/wp/inside_out/api/,conservation"));
        
        i.add(new NavItem("Wallpaper Tumblr", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, TumblrFragment.class, "androidbackgrounds"));
        
        i.add(new NavItem("3FM Radio", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, MediaFragment.class, "http://yp.shoutcast.com/sbin/tunein-station.m3u?id=709809"));
        i.add(new NavItem("Official Twitter", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, TweetsFragment.class, "Android"));
        i.add(new NavItem("Maps", com.homains.daliloffline.R.drawable.ic_details, NavItem.ITEM, MapsFragment.class, "drogisterij"));
        
        //It's Suggested to not change the content below this line
        
        i.add(new NavItem("Device", NavItem.SECTION));
        i.add(new NavItem("Favorites", com.homains.daliloffline.R.drawable.ic_action_favorite, NavItem.EXTRA, FavFragment.class, null));
        i.add(new NavItem("Settings", com.homains.daliloffline.R.drawable.ic_action_settings, NavItem.EXTRA, SettingsFragment.class, null));
        
        //DONT MODIFY BELOW THIS LINE
        
        return i;
			
	}
	
}