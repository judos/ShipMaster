package controller;

import java.util.Iterator;
import model.Attention;
import model.Map;


/**
 * @since 25.05.2015
 * @author Julian Schelker
 */
public class AttentionController {

	private Map	map;

	public AttentionController(Map map) {
		this.map = map;
	}

	public void update() {
		Iterator<Attention> it = this.map.getAttentions().iterator();
		while(it.hasNext()) {
			Attention att = it.next();
			if (att.shouldBeDeleted())
				it.remove();
		}
	}

}
