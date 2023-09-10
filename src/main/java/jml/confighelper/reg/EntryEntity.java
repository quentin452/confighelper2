package jml.confighelper.reg;

import net.minecraft.entity.Entity;

public class EntryEntity {

    public Class<? extends Entity> clazz;
    public String name;

    public EntryEntity(Class<? extends Entity> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

}
