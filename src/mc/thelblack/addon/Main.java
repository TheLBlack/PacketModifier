package mc.thelblack.addon;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
			@Override
			public void onPacketSending(PacketEvent event) {
				int id = event.getPacket().getIntegers().read(0);

				if (id != event.getPlayer().getEntityId()) {
					List<WrappedWatchableObject> t = event.getPacket().getWatchableCollectionModifier().read(0);
					t.forEach(a -> {
						if (a.getIndex() == 8) if (a.getValue() instanceof Float) a.setValue(20f);
					});
					
					event.getPacket().getWatchableCollectionModifier().write(0, t);
				}
				else {
					List<WrappedWatchableObject> t = event.getPacket().getWatchableCollectionModifier().read(0);
					t.forEach(a -> {
						if (a.getIndex() == 8) if (a.getValue() instanceof Float) a.setValue(Float.valueOf(String.valueOf(event.getPlayer().getHealth())));
					});
					
					event.getPacket().getWatchableCollectionModifier().write(0, t);
				}
			}
		});
	}
}
