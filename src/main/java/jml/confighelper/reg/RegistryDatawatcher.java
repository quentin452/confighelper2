package jml.confighelper.reg;

import java.util.Set;

import jml.confighelper.RegistryConfig;

public class RegistryDatawatcher extends RegistryInt {

    public RegistryDatawatcher() {
        super(DataType.DATAWATCHER);
    }

    @Override
    public Set<Integer> getPassableIds() {
        return RegistryConfig.passableWatcherIds;
    }
}
