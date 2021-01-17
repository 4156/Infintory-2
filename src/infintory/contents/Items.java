package infintory.contents;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

import java.util.ArrayList;

public class Items implements ContentList {
    public static Item compact_alloy;
    public ArrayList<Item> items=new ArrayList<>();
    @Override
    public void load() {
        compact_alloy=new Item("compact-alloy"){{
            description="The basic alloy cast by copper and lead has good hardness and toughness";
            color= Color.brick;
            clearUnlock();
        }};
        items.add(compact_alloy);
    }
}
