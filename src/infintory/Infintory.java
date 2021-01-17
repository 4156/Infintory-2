package infintory;

import arc.Events;
import infintory.contents.Blocks;
import infintory.contents.Items;
import infintory.contents.Recipes;
import infintory.contents.Techs;
import infintory.types.logic.Recipe;
import infintory.ui.ResearchFragment;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;

public class Infintory extends Mod{

    public Infintory(){
        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            ResearchFragment fragment = new ResearchFragment();
            fragment.build(Vars.ui.hudGroup);
            Events.on(EventType.WorldLoadEvent.class, (et) -> {
                fragment.table.visible = true;
            });
        });
    }

    @Override
    public void loadContent()
    {
        new Items().load();
        new Recipes().load();
        new Blocks().load();
        new Techs().load();
    }
}
