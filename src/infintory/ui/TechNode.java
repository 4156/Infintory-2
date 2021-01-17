package infintory.ui;

import arc.Core;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.Cicon;
import mindustry.ui.ItemDisplay;
import mindustry.ui.dialogs.AboutDialog;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.ContentInfoDialog;

import java.util.ArrayList;

public class TechNode{
    public String name,information,image;
    public ArrayList<UnlockableContent> contents=new ArrayList<>();
    public Runnable runnable;
    public Runnable done;
    public ItemStack[] stacks;
    public int timeCost=100;
    public boolean isLocked=true;
    public int progress=0;
    public TechNode(String name, String image,ArrayList<TechNode> list){
        this.image=image;
        this.name=name;
        list.add(this);
    }
    public void displayDialog(ItemStack... stacks){
        this.stacks=stacks;
        TechDialog dialog=new TechDialog("Tech Information",this){{ addCloseButton();
        cont.image(Core.atlas.find("age-"+node.image));
        cont.row();
        cont.label(()->node.name);
        cont.row();
        cont.label(()->node.information);
        cont.row();
        cont.pane((e)->{
            e.top();for(int i=0;i<stacks.length;i++){
                    e.add(new ItemDisplay(stacks[i].item,stacks[i].amount)).size(800,50);
                    e.row();
                }
        }).size(1000,200);
        cont.row();
        cont.label(()->"@time-usage "+timeCost);
        cont.row();
        if(contents.isEmpty()==false) {
            cont.label(() -> "@unlock");
            cont.row();
            cont.pane((e)->{
                for(int i=0;i<contents.size();i++){
                    int b=i;
                    e.button("@"+contents.get(i).name,new TextureRegionDrawable(contents.get(i).icon(Cicon.full)),()->{
                        ContentInfoDialog dialog1=new ContentInfoDialog();
                        dialog1.show(contents.get(b));
                    });
                }
            }).size(1000,200);
        }
        cont.row();cont.button("Research",()->{
                ResearchFragment.node=this.node;
                this.visible=false;
            }).size(750,100);
        }};
        dialog.show();
    }
    public void customDialog(Table cont){

    }

    public class TechDialog extends BaseDialog {
        public TechNode node;
        public TechDialog(String title,TechNode node) {
            super(title);
            this.node=node;
        }
    }

}
