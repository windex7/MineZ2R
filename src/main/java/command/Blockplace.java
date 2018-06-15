package command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import main.MineZ2R;


public class Blockplace implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//if !(sender instanceof BlockCommandSender) return false;
		if (sender instanceof BlockCommandSender) {
			// args: e.g. x1:x2!randx2,y,z1:z2,block:datavalue,start:enddelay,mode,issound:filename,isrelative;x,y,z,...
			if (!(args.length == 1)) return false;
			//int maxproject = 1024;
			int maxargs = 20;
			String defaultsound = "soundlocation"; // rewrite to the correct sound location
			Location blocklocation = ((BlockCommandSender)sender).getBlock().getLocation();
			Vector blockvector = blocklocation.toVector();
			String[] projectraw = args[0].split(";");
			int projectrawlength = projectraw.length;
			String[][] project = new String[projectrawlength][maxargs];
			// -- projectcontents[i][0]: isrelativecoord [1-6]: start,endpoint [7-12]: start,endrand [13,14]: block,datavalue [15,16]: start,enddelay [17]: mode [18,19]: sound,filename
			for (int i = 0; i < projectrawlength; i++) {
				String[] projectstring = projectraw[i].split(",");
				if (projectstring.length < 4) continue; // -- projectstring[3] = null: ignore
				// -- start/end vector --
				String[] x = projectstring[0].split(":");
				String[] y = projectstring[1].split(":");
				String[] z = projectstring[2].split(":");
				String[] x1 = x[0].split("!");
				String[] y1 = y[0].split("!");
				String[] z1 = z[0].split("!");
				project[i][1] = x1[0];
				project[i][2] = y1[0];
				project[i][3] = z1[0];
				if (x1.length > 1) project[i][7] = String.valueOf((int)(Math.random()*(Integer.parseInt(x1[1]) + 1)));
				else project[i][7] = "0";
				if (y1.length > 1) project[i][8] = String.valueOf((int)(Math.random()*(Integer.parseInt(y1[1]) + 1)));
				else project[i][8] = "0";
				if (z1.length > 1) project[i][9] = String.valueOf((int)(Math.random()*(Integer.parseInt(z1[1]) + 1)));
				else project[i][9] = "0";
				if (x.length > 1) {
					String[] x2 = x[1].split("!");
					project[i][4] = x2[0];
					if (x2.length > 1) project[i][10] = String.valueOf((int)(Math.random()*(Integer.parseInt(x2[1]) + 1)));
					else project[i][10] = "0";
				}
				else {
					project[i][4] = String.valueOf(Integer.parseInt(project[i][1]) + Integer.parseInt(project[i][7]));
					project[i][10] = "0";
				}
				if (y.length > 1) {
					String[] y2 = y[1].split("!");
					project[i][5] = y2[0];
					if (y2.length > 1) project[i][11] = String.valueOf((int)(Math.random()*(Integer.parseInt(y2[1]) + 1)));
					else project[i][11] = "0";
				}
				else {
					project[i][5] = String.valueOf(Integer.parseInt(project[i][2]) + Integer.parseInt(project[i][8]));
					project[i][11] = "0";
				}
				if (z.length > 1) {
					String[] z2 = z[1].split("!");
					project[i][6] = z2[0];
					if (z2.length > 1) project[i][12] = String.valueOf((int)(Math.random()*(Integer.parseInt(z2[1]) + 1)));
					else project[i][12] = "0";
				}
				else {
					project[i][6] = String.valueOf(Integer.parseInt(project[i][3]) + Integer.parseInt(project[i][9]));
					project[i][12] = "0";
				}
				// -- block, datavalue --
				String[] block = projectstring[3].split(":");
				project[i][13] = block[0];
				if (block.length > 1) project[i][14] = block[1];
				else project[i][14] = "0";
				// -- start/end delay --
				if (projectstring.length > 4) {
					String[] delay = projectstring[4].split(":");
					project[i][15] = delay[0];
					if (delay.length > 1) project[i][16] = delay[1];
					else project[i][16] = "0";
				}
				else {
					project[i][15] = "0";
					project[i][16] = "0";
				}
				// -- mode --
				if (projectstring.length > 5) project[i][17] = projectstring[5]; // mode: 0: clear block when redstone signal is turned off 1: clear when start+enddelay goes
				else if (project[i][16].equals("0")) project[i][17] = "0";
				else project[i][17] = "1";
				// -- sound --
				if (projectstring.length > 6) {
					String[] sound = projectstring[6].split(":");
					project[i][18] = sound[0];
					if (sound.length > 1) project[i][19] = sound[1];
					else project[i][19] = defaultsound;
				}
				else {
					project[i][18] = "false";
					project[i][19] = defaultsound;
				}
				// -- isrelativecoord --
				if (projectstring.length > 7)  project[i][0] = projectstring[7];
				else project[i][0] = "false";
			}

			for (int i = 0; i < project.length; i++) {
				boolean isrelativecoord = Boolean.valueOf(project[i][0]);
				Vector startpoint = new Vector(Integer.parseInt(project[i][1]), Integer.parseInt(project[i][2]), Integer.parseInt(project[i][3]));
				Vector endpoint = new Vector(Integer.parseInt(project[i][4]), Integer.parseInt(project[i][5]), Integer.parseInt(project[i][6]));
				Vector randomstart = new Vector(Integer.parseInt(project[i][7]), Integer.parseInt(project[i][8]), Integer.parseInt(project[i][9]));
				Vector randomend = new Vector(Integer.parseInt(project[i][10]), Integer.parseInt(project[i][11]), Integer.parseInt(project[i][12]));
				String block = project[i][13];
				int blockdatavalue = Integer.parseInt(project[i][14]);
				int startdelay = Integer.parseInt(project[i][15]);
				int enddelay = Integer.parseInt(project[i][16]);
				int mode = Integer.parseInt(project[i][17]);
				boolean issound = Boolean.valueOf(project[i][18]);
				String soundfile = project[i][19];
				constructProject(isrelativecoord, blocklocation, blockvector, startpoint, endpoint, randomstart, randomend, block, blockdatavalue, startdelay, enddelay, mode, issound, soundfile);
			}
			return true;
		}
		return false;
	}

	public static void constructProject(boolean isrelativecoord, Location senderlocation, Vector senderblock, Vector startpoint, Vector endpoint, Vector randomstart, Vector randomend, String block, int blockdatavalue, int startdelay, int enddelay, int mode, boolean issound, String soundfile) {
		//Vector randomstartxyz = new Vector((int)(Math.random()*randomstart.getX()), (int)(Math.random()*randomstart.getY()), (int)(Math.random()*randomstart.getZ()));
		//Vector randomendxyz = new Vector((int)(Math.random()*randomend.getX()), (int)(Math.random()*randomend.getY()), (int)(Math.random()*randomend.getZ()));
		Plugin plugin = MineZ2R.getInstance();
		// Block commandblock = senderlocation.getBlock();
		Vector start = startpoint.add(randomstart).add(senderblock);
		Vector end = endpoint.add(randomend).add(senderblock);
		if (startdelay == 0) {
			setblock(start, end, block, blockdatavalue);
		}
		else {
			// delay(setblock)
			plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
				public void run() {
					setblock(start, end, block, blockdatavalue);
				}
			}, (startdelay));
		}
		//if (enddelay == 0) {
		if (mode == 0) {
			// see if the commandblock is powered and run clearblock when it is turned off

		}
		else {
			// delay(clearblock)
			plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
				public void run() {
					setblock(start, end, "air", blockdatavalue);
				}
			}, (startdelay + enddelay));
		}
		return;
	}

	public static void setblock(Vector start, Vector end, String block, int blockdatavalue) {
		Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "minecraft:fill " + String.valueOf((int)start.getX()) + " " +  String.valueOf((int)start.getY() + " " + String.valueOf((int)start.getZ()) + " " + String.valueOf((int)end.getX()) + " " + String.valueOf((int)end.getY()) + " " + String.valueOf((int)end.getZ()) + " " + block + " " + String.valueOf(blockdatavalue)));
		return;
	}
}