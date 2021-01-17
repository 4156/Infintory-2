package infintory.types.block;

import arc.Core;
import arc.Events;
import arc.func.Func;
import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import infintory.types.logic.Recipe;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.ui.Bar;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.draw.DrawBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.modules.ConsumeModule;

import java.util.ArrayList;

public class SCrafter extends GenericCrafter {
    public ArrayList<Recipe> recipes=new ArrayList<>();
    public boolean hasHeat;
    public int heatCapacity=0;
    public int heats=0;
    public SCrafter(String name) {
        super(name);
        hasLiquids=true;
        liquidCapacity=20;
        Events.on(EventType.WorldLoadEvent.class,(e)->{
            clearUnlock();
        });
    }
    @Override
    public boolean isVisible() {
        return !locked();
    }
    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speed, craftTime, StatUnit.seconds);
        if(recipes != null){
            for(int i=0;i<recipes.size();i++){
                stats.add(Stat.output,"@"+recipes.get(i).name);
            }
        }
        if(hasHeat){
            stats.add(Stat.input,"@heat-capacity"+" "+heatCapacity);
        }
    }

    @Override
    public void setBars() {
        super.setBars();
        if(hasHeat){
            bars.add("heat", (SCrafterBuild building)-> new Bar("@heat "+heats, Pal.ammo,()->heats%heatCapacity));
        }
    }

    public class SCrafterBuild extends GenericCrafterBuild {
        public Item fuel;
        public Recipe recipe;
        public int  progress;

        @Override
        public boolean acceptItem(Building source, Item item) {
            if(recipe!=null) {
                if(recipe.itemInput!=null){
                    for (int i=0;i<recipe.itemInput.size();i++) {
                        if(recipe.itemInput.get(i).item==item){
                            if(items.get(item)<itemCapacity){
                                return true;
                            }
                        }
                    }
                }
                if(recipe.heatConsume>0){
                    if(item.flammability>0){
                        if(items.get(item)<itemCapacity){
                            fuel=item;
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            if(recipe!=null) {
                if(recipe.liquidInput!=null) {
                    if (liquid == recipe.liquidInput.liquid&&liquids.total()<liquidCapacity) {
                            return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void display(Table table) {
            super.display(table);
            table.row();
            table.top();
            if(recipe!=null) {
                for (int t = 0; t < recipe.itemInput.size(); t++) {
                    table.add(new ItemDisplay(recipe.itemInput.get(t).item,recipe.itemInput.get(t).amount));
                }
                if (recipe.liquidInput != null) {
                    table.add(new LiquidDisplay(recipe.liquidInput.liquid,recipe.liquidInput.amount,false));
                }
                table.image(Icon.craftingSmall);
                for (int t = 0; t < recipe.itemOutput.size(); t++) {
                    table.add(new ItemDisplay(recipe.itemOutput.get(t).item,recipe.itemOutput.get(t).amount));
                }
                if (recipe.liquidOutput != null) {
                    table.add(new LiquidDisplay(recipe.liquidOutput.liquid,recipe.liquidOutput.amount,false));
                }
            }
        }

        @Override
        public void updateTile(){
            if(fuel!=null){
                if(items.has(fuel)){
                    if(fuel.flammability+heats<heatCapacity) {
                        items.remove(fuel, 1);
                        heats = (int) (heats + fuel.flammability);
                    }
                }
            }
            if(recipe!=null){
                if(cons.valid()){
                    if(recipe.liquidInput==null){
                        if (recipe.itemInput.isEmpty() == false && items.has(recipe.itemInput)) {
                            progress++;
                        }
                    }
                    if(recipe.liquidInput!=null){
                        if (recipe.itemInput.isEmpty() == true && liquids.current() == recipe.liquidInput.liquid) {
                            if (liquids.get(recipe.liquidInput.liquid)> recipe.liquidInput.amount) {
                                progress++;
                            }
                        }
                        if(recipe.itemInput.isEmpty()==false && items.has(recipe.itemInput)&&liquids.current()==recipe.liquidInput.liquid){
                            if(liquids.get(recipe.liquidInput.liquid)>recipe.liquidInput.amount){
                                progress++;
                            }
                        }
                    }
                    if(progress>=recipe.time){
                        if(recipe.itemInput.isEmpty()==false){
                            items.remove(recipe.itemInput);
                        }
                        if(recipe.liquidInput!=null){
                            liquids.remove(recipe.liquidInput.liquid,recipe.liquidInput.amount);
                        }
                        if(recipe.itemOutput.isEmpty()==false){
                            int amount=0;
                            for(int i=0;i<recipe.itemOutput.size();i++){
                                amount=amount+recipe.itemOutput.get(i).amount;
                            }
                            if(amount<items.total()){
                                for(int b=0;b<recipe.itemOutput.size();b++) {
                                    this.items.add(recipe.itemOutput.get(b).item,recipe.itemOutput.get(b).amount);
                                }
                            }
                        }
                        if(recipe.liquidOutput!=null){
                            this.handleLiquid(this, recipe.liquidOutput.liquid, recipe.liquidOutput.amount);
                        }
                        if(recipe.heatConsume>0){
                            heats=heats-recipe.heatConsume;
                        }
                        progress=0;
                    }
                    if (this.timer(SCrafter.this.timerDump, 1.0F)) {
                        for(int i=0;i<recipe.itemOutput.size();i++) {
                            this.dump(recipe.itemOutput.get(i).item);
                        }
                    }
                    if (recipe.liquidOutput!= null) {
                        this.dumpLiquid(recipe.liquidOutput.liquid);
                    }
                }
            }
        }


        @Override
        public void buildConfiguration(Table table) {
            table.button(Icon.add,()->{
                BaseDialog dialog=new BaseDialog("Choose Recipe"){{
                    addCloseButton();
                    for(int i=0;i<recipes.size();i++){
                        if(recipes.get(i).unlocked()){
                            int b=i;
                            cont.button(recipes.get(i).name, new TextureRegionDrawable(Core.atlas.find("age-"+recipes.get(i).drawable)),()->{
                                recipe=recipes.get(b);
                                visible=false;
                                items.clear();
                                liquids.clear();
                            }).size(500,50);
                            cont.row();
                        }
                    }
                    cont.row();
                    if(recipe!=null) {
                        cont.label(() -> "Recipe-" + recipe.name);
                    }
                }};
                dialog.show();
            });
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(progress);
        }
        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.i();
        }
    }
}
