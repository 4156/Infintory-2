package infintory.contents;

import arc.Core;
import arc.scene.style.Drawable;
import infintory.types.logic.Recipe;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;

import java.util.Arrays;

public class Recipes implements ContentList {
    public static Recipe compact_alloy;
    @Override
    public void load() {
        compact_alloy=new Recipe("compact-alloy"){{
            time=180;
            itemInput.addAll(Arrays.asList(give(new ItemStack(Items.copper,2),new ItemStack(Items.lead,1))));
            heatConsume=10;
            itemOutput.add(new ItemStack(infintory.contents.Items.compact_alloy,1));
            alwaysUnlocked=true;
        }};
    }
    public ItemStack[] give(ItemStack... stacks){
        return stacks;
    }
}
