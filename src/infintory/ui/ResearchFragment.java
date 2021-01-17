package infintory.ui;

import arc.Core;
import arc.scene.Group;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import infintory.Infintory;
import infintory.contents.Techs;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;

public class ResearchFragment extends mindustry.ui.fragments.Fragment {
    public Table table;
    public float progress=0.2f;
    public static TechNode node;
    @Override
    public void build(Group group) {
        group.fill((table)->{
            this.table=table;
            table.top();
            table.right();
            table.row();
            table.button(Icon.tree,()->{
                BaseDialog dialog=new BaseDialog("tech");
                dialog.addCloseButton();
                dialog.cont.top();
                dialog.cont.pane((e)->{
                    for(int i=0;i<Techs.nodes.size();i++){
                        e.row();
                        e.button(Techs.nodes.get(i).name, new TextureRegionDrawable(Core.atlas.find("age-"+Techs.nodes.get(i).image)),Techs.nodes.get(i).runnable).size(800,100);
                    }
                }).size(1000,1500);
                dialog.show();
            });
            table.row();
            table.label(()->""+progress);
            });
        };
    }


