package me.cade.vanabyte.NPCS.PacketHolograms;

public class PHologramUnused {

//    public void teleport(Location location) {
//        this.location = location;
//        ProtocolLibrary.getProtocolManager().broadcastServerPacket(createMovePacket());
//    }
//
//    private PacketContainer createMovePacket() {
//        PacketType type = PacketType.Play.Server.ENTITY_TELEPORT;
//        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);
//
//        packet.getIntegers().write(0, entityId);
//
//        StructureModifier<Double> doubleMod = packet.getDoubles();
//        doubleMod.write(0, this.location.getX());
//        doubleMod.write(1, this.location.getY());
//        doubleMod.write(2, this.location.getZ());
//
//        return packet;
//    }
//
//    private PacketContainer createRemovePacket() {
//        //Get the entity destroy packet that comes from the SERVER -> CLIENT
//        PacketType type = PacketType.Play.Server.ENTITY_DESTROY;
//
//        //Create the packet container to modify
//        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);
//
//        //List of entities to destroy, supposed to be Array of VarInt, but this may work
//        ArrayList<Integer> entities = new ArrayList<Integer>();
//        entities.add(this.entityId);
//
//        //Write this to the FIRST (index 0) int list of the packet type, which takes a list of entities to destroy
//        packet.getIntLists().write(0, entities);
//
//        return packet;
//    }
//
//    public void hideFrom(Player player) throws InvocationTargetException {
//        ProtocolLibrary.getProtocolManager().sendServerPacket(player, createRemovePacket());
//    }

}
