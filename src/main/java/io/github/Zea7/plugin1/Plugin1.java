package io.github.Zea7.plugin1;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin1 extends JavaPlugin {

    public static int set_Yield=10;
    @Override
    public void onEnable() {
        getLogger().info("===================================="+"\r\n"+"\t"+"플러그인이 활성화 되었습니다."+"\r\n"+"====================================");


    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }



    @Override
    public boolean onCommand(CommandSender sender,Command cmd, String label, String[] args){
        Event e = new Event();
        String s=cmd.getName();
        if(s.equalsIgnoreCase("z")){
            if(args.length==0){
                sender.sendMessage(ChatColor.RED + "명령어를 입력하세요.");
                sender.sendMessage(ChatColor.YELLOW + "도움을 원하시면 /z help 를 입력하세요.");
            }
            else if(args[0].equalsIgnoreCase("help")){
                sender.sendMessage(ChatColor.YELLOW + "This Plugin is Made By Zea");
                sender.sendMessage(ChatColor.YELLOW + "/z on : tntArrow를 활성화 합니다.");
                sender.sendMessage(ChatColor.YELLOW + "/z off : tntArrow를 비활성화 합니다.");
                sender.sendMessage(ChatColor.YELLOW + "/z set : tntArrow의 기본 값을 바꿉니다.");
            } else if (args[0].equalsIgnoreCase("on")) {
                getServer().getPluginManager().registerEvents(e, this);
                sender.sendMessage(ChatColor.YELLOW+"tntArrow가 활성화 되었습니다.");
            }else if(args[0].equalsIgnoreCase("off")) {
                ProjectileHitEvent.getHandlerList().unregister(this);
                sender.sendMessage(ChatColor.YELLOW + "tntArrow가 비활성화 되었습니다.");
            }else if (args[0].equalsIgnoreCase("set")) {
                set_Yield = Integer.parseInt(args[1]);
                sender.sendMessage(ChatColor.YELLOW + "폭발 범위를 "+set_Yield+"배로 설정했습니다.");
                getServer().getPluginManager().registerEvents(e, this);
            }else {
                sender.sendMessage(ChatColor.RED + "명령어가 정확하지 않습니다.");
            }

        }


        return true;
    }
}

class Event implements Listener{
    @EventHandler
    public void tntArrow(ProjectileHitEvent e) {
        if(e.getEntityType()!=EntityType.ARROW) return;
        Location place = e.getEntity().getLocation();
        TNTPrimed tnt = (TNTPrimed)e.getEntity().getWorld().spawnEntity(place, EntityType.PRIMED_TNT);
        tnt.setSilent(true);
        tnt.setYield(Plugin1.set_Yield);
        tnt.setFuseTicks(0);
        e.getEntity().remove();
    }
}
