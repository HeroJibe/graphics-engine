package org.vrhel.graphics;

import java.util.ArrayList;

/**
 * Stores all buffers in the current
 * engine context.
 * 
 * @author Ethan Vrhel
 * @since 1.2
 */
public class AbstractBufferHandler {

	private static AbstractBufferHandler handler;
	
	private ArrayList<AbstractBuffer> buffers;
	private AbstractBuffer boundBuffer;
	
	private AbstractBufferHandler() { 
		buffers = new ArrayList<AbstractBuffer>();
	}
	
	static void init() {
		handler = new AbstractBufferHandler();
	}
	
	/**
	 * Gets the current buffer handler associated with
	 * the current context of the engine.
	 * 
	 * @return The current <code>AbstractBufferHandler</code>.
	 */
	public static AbstractBufferHandler getHandler() {
		return handler;
	}
	
	static void destroyHandler() {
		handler.destroy();
		handler = null;
	}
	
	private void destroy() {
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				buffers.get(i).destroy();
				buffers.set(i, null);
			}
		}
		buffers = null;
	}
	
	void add(AbstractBuffer buffer) {
		if (! buffers.contains(buffer))
			buffers.add(buffer);
	}
	
	AbstractBuffer get(int id) {
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				if (buffers.get(i).getID() == id)
					return buffers.get(i);
			}
		}
		return null;
	}
	
	void render() {
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				if (buffers.get(i).isEnabled()) {
					buffers.get(i).render();
				}
			}
		}
	}
	
	/**
	 * Binds a buffer to which objects will
	 * be added to the buffer.
	 * 
	 * @param id The buffer's id.
	 * @throws IllegalArgumentException When the buffer
	 * does not exist.
	 */
	public void bind(int id) throws IllegalArgumentException {
		if (id < 0) {
			boundBuffer = null;
			return;
		}
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				if (buffers.get(i).getID() == id) {
					boundBuffer = buffers.get(i);
					return;
				}
			}
		}
		boundBuffer = null;
		throw new IllegalArgumentException("No buffer with id \"" + id + "\" exists.");
	}
	
	AbstractBuffer getBoundBuffer() {
		return boundBuffer;
	}
}
