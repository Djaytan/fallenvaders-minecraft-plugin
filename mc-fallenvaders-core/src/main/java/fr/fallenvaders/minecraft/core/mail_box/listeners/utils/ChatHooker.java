package fr.fallenvaders.minecraft.core.mail_box.listeners.utils;

import fr.fallenvaders.minecraft.core.FallenVadersCorePlugin;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.core.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.core.mail_box.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class ChatHooker {
	
	private static Map<UUID, ChatHooker> map = new HashMap<>();
	
	private String Id;
	private UUID target;
	private Listener listener;
	private Consumer<AsyncPlayerChatEvent> execution;
	private String startMessage;

	public ChatHooker(String id, String startMessage) {
		this.setId(id);
		this.setStartMessage(startMessage);
		
	}

	public void start(Player player) {
		MessageUtils.sendMessage(player, MessageLevel.INFO, startMessage + " " + LangManager.getValue("information_ch_stop_selection"));
		
		this.setTarget(player.getUniqueId());
		this.setListener(new Listener() {

			@EventHandler
			private void onChatHooking(AsyncPlayerChatEvent event) {
				if (event.getPlayer().getUniqueId().equals(getTarget())) {
					getExecution().accept(event);
				}
			}

		});

		FallenVadersCorePlugin.main.getServer().getPluginManager().registerEvents(this.getListener(), FallenVadersCorePlugin.main);
		this.load(this.getTarget());
	}

	public void stop() {
		this.unload(this.getTarget());
        AsyncPlayerChatEvent.getHandlerList().unregister(this.getListener());
	}
	
	public void load(UUID uuid) {
		map.put(uuid, this);
	}
	
	public void unload(UUID uuid) {
		map.remove(uuid);
	}
	
	public static ChatHooker get(UUID uuid) {
        return map.get(uuid);
    }
	
	public UUID getTarget() {
		return target;
	}

	public void setTarget(UUID target) {
		this.target = target;
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public Consumer<AsyncPlayerChatEvent> getExecution() {
		return execution;
	}

	public void setExecution(Consumer<AsyncPlayerChatEvent> execution) {
		this.execution = execution;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getStartMessage() {
		return startMessage;
	}

	public void setStartMessage(String startMessage) {
		this.startMessage = startMessage;
	}

}
