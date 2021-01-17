package infintory.types.logic;

import arc.scene.style.Drawable;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Icon;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.meta.Stat;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe extends UnlockableContent {
    public int time=60;
    public boolean isLocked=true;
    public ArrayList<ItemStack> itemInput,itemOutput;
    public LiquidStack liquidInput,liquidOutput;
    public int heatConsume=0;
    public int powerUsage=0;
    public String drawable;
    public Recipe(String name){
        super(name);
        itemInput=new ArrayList<>();
        itemOutput=new ArrayList<>();
    }

    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }

    @Override
    public void setStats() {
        for (int i = 0; i < itemInput.size(); i++) {
            stats.add(Stat.input, itemInput.get(i));
        }
        if(liquidInput!=null) {
            stats.add(Stat.input,liquidInput.liquid,liquidInput.amount,false);
        }
        for (int i = 0; i < itemOutput.size(); i++) {
            stats.add(Stat.output, itemInput.get(i));
        }
        if(liquidOutput!=null){
            stats.add(Stat.output,liquidOutput.liquid,liquidOutput.amount,false);
        }
        stats.add(Stat.input,"@time-usage"+" "+time);
    }

    public void addInputItems(ItemStack... stacks){
        itemInput.addAll(Arrays.asList(stacks));
    }
    public void addOutputItems(ItemStack... stacks){
        itemOutput.addAll(Arrays.asList(stacks));
    }
    public void addInputLiquid(LiquidStack stack){
        liquidInput=stack;
    }
    public void addOutputLiquid(LiquidStack stack){
        liquidOutput=stack;
    }
}
