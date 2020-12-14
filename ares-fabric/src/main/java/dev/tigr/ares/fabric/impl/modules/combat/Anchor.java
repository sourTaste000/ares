package dev.tigr.ares.fabric.impl.modules.combat;

import dev.tigr.ares.core.feature.module.Category;
import dev.tigr.ares.core.feature.module.Module;
import dev.tigr.ares.core.setting.Setting;
import dev.tigr.ares.core.setting.settings.numerical.IntegerSetting;
import dev.tigr.ares.fabric.utils.HoleType;
import dev.tigr.ares.fabric.utils.WorldUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/**
 * @author Tigermouthbear 12/13/20
 */
@Module.Info(name = "Anchor", description = "Pulls you into holes fast", category = Category.COMBAT)
public class Anchor extends Module {
    private final Setting<Integer> max = register(new IntegerSetting("Max Distance", 5, 1, 20));

    @Override
    public void onMotion() {
        if(MC.player != null && isOverHole(MC.player) && MC.player.getVelocity().y <= 0) MC.player.setVelocity(0, -20, 0);
    }

    private boolean isOverHole(PlayerEntity playerEntity) {
        BlockPos pos = playerEntity.getBlockPos();
        int num = 0;

        while(WorldUtils.isHole(pos) == HoleType.NONE) {
            pos = pos.down();
            if(num++ >= max.getValue()) return false;
        }
        return true;
    }
}
