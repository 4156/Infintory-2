package infintory.types.block;



import arc.scene.ui.layout.Table;
import infintory.ui.ResearchFragment;

import infintory.ui.TechNode;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;



public class ResearchBase extends GenericCrafter {
    public int speed=1;
    public ResearchBase(String name) {
        super(name);
        itemCapacity=1000;
    }
    public class ResearchBuilding extends GenericCrafterBuild{
        @Override
        public void display(Table table) {
            super.display(table);
            if(node!=null){
                table.label(()->"@progress "+node.progress%node.timeCost+"%");
            }
        }

        public TechNode node;
        public int progress;
        public int timeDone;

        public float progress(){
            if(node!=null&&progress!=0){
                return progress%(node.timeCost*speed);
            }
            return 0;
        }
        @Override
        public void updateTile() {
            if(node==null||node!=ResearchFragment.node) {
                node = ResearchFragment.node;
                progress=0;
            }
            if(node!=null){
                if(node.isLocked){
                    if(items.has(node.stacks)){
                       timeDone++;
                    }
                }
                if(timeDone>60%speed){
                    for(int i=0;i<node.stacks.length;i++){
                        items.remove(node.stacks);
                    }
                    timeDone=0;
                    node.progress++;
                }
                if(node.progress>=node.timeCost){
                    node.isLocked=false;
                    node.done.run();
                    progress=0;
                }
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if(node!=null) {
                if(node.stacks!=null){
                    for (int i=0;i<node.stacks.length;i++) {
                        if(node.stacks[i].item==item){
                            if(items.get(item)<itemCapacity){
                                return true;
                            }
                        }
                    }
                }
            }
            return  false;
        }
    }
}
