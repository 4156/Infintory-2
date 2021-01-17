package infintory.contents;

import infintory.ui.TechNode;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.ItemStack;

import java.util.ArrayList;

public class Techs implements ContentList {
    public static TechNode basic_alloy_smelting,basic_raw_material_treatment;
    public static ArrayList<TechNode> nodes;
    @Override
    public void load() {
        nodes=new ArrayList<>();
        /*basic_alloy_smelting=new TechNode("@basic-alloy-smelting","alloy-furnace",nodes){{
            information="@basic-alloy-smelting-des";
            runnable=()->{
                basic_alloy_smelting.displayDialog(new ItemStack(Items.copper,20),new ItemStack(Items.coal,20));
            };
            contents.add(Blocks.alloy_pot);
            done=()->{Blocks.alloy_pot.unlock();};
        }};*/
    }
}
