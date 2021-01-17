package infintory.contents;

import infintory.types.block.ResearchBase;
import infintory.types.block.SCrafter;
import infintory.types.logic.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.BuildVisibility;

import java.util.ArrayList;

import static java.time.zone.ZoneOffsetTransitionRule.TimeDefinition.WALL;
import static mindustry.type.ItemStack.with;

public class Blocks implements ContentList {
    public static Block small_alloy_kiln;
    @Override
    public void load() {
        /*alloy_pot=new SCrafter("alloy-pot"){{
            health=2000;
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 115, Items.graphite, 25, Items.titanium, 100));
            size=3;
            liquidCapacity=20;
            recipes.add(Recipes.charcoal);
            recipes.add(Recipes.steel);
            configurable=true;
        }};
        tech_base=new ResearchBase("oven"){{
            health=2000;
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 115, Items.graphite, 25, Items.titanium, 100));
            size=2;
        }};*/
        small_alloy_kiln=new SCrafter("small-alloy-kiln"){{
            health=200;
            requirements(Category.crafting, with(Items.copper,100, Items.lead,50));
            size=2;
            configurable=true;
            hasHeat=true;
            heatCapacity=100;
            alwaysUnlocked=true;
            recipes.add(Recipes.compact_alloy);
        }};

    }

}
