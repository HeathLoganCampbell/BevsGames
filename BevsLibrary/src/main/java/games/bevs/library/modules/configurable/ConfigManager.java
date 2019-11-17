package games.bevs.library.modules.configurable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import games.bevs.library.commons.utils.ClassGetterUtils;
import games.bevs.library.commons.EasyConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class ConfigManager {
	private HashMap<String, EasyConfig> configs = new HashMap<>();
	private JavaPlugin plugin;
	private JavaPlugin ownerPlugin;
	private String packagePath;

	public ConfigManager(JavaPlugin plugin, JavaPlugin ownerPlugin, String packagePath) {
		this.plugin = plugin;
		this.ownerPlugin = ownerPlugin;
		this.packagePath = packagePath;
		updateOptionals();
	}

	public void updateOptionals() {
		ClassGetterUtils.getClassesForPackage(this.plugin, packagePath).forEach(clazz -> {
			if (clazz.isAnnotationPresent(Configurable.class)) {


				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);

					if (field.isAnnotationPresent(Optional.class))
					{
						Optional optionalToken = field.getAnnotation(Optional.class);
						String name = field.getName();
						String path = optionalToken.path() + "."
								+ (name.length() > 0 ? name : field.getName());

						String fileName = optionalToken.fileName();

						try {
							if (field.getType() == String.class) {
								if (getConfig(fileName).get(path) == null) {
									getConfig(fileName).set(path, field.get(null));
									saveConfig(fileName);
								} else {
									field.set(null, getConfig(fileName).get(path));
								}
							} if (field.getType() == Long.class) {
								if (getConfig(fileName).get(path) == null) {
									getConfig(fileName).set(path, field.get(null));
									saveConfig(fileName);
								} else {
									field.set(null, getConfig(fileName).getLong(path));
								}
							} if (field.getType() == Boolean.class) {
								if (getConfig(fileName).get(path) == null) {
									getConfig(fileName).set(path, field.get(null));
									saveConfig(fileName);
								} else {
									field.set(null, getConfig(fileName).getBoolean(path));
								}
							} else if (field.getType() == List.class) {
								if (getConfig(fileName).getList(path) == null) {
									getConfig(fileName).set(path, field.get(null));
									saveConfig(fileName);
								} else {
									field.set(null, getConfig(fileName).getList(path));
								}
							} else if (field.getType() == Integer.class) {
								if (!getConfig(fileName).contains(path)) {
									getConfig(fileName).set(path, field.get(null));
									saveConfig(fileName);
								} else {
									field.set(null, getConfig(fileName).getInt(path));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					field.setAccessible(false);
				}
			}
		});
	}

	private void saveConfig(String fileName) {
		EasyConfig config = this.configs.get(fileName);
		if (config == null) {
			config = new EasyConfig(fileName, ownerPlugin);
			this.configs.put(fileName, config);
		}

		config.save();
	}

	private FileConfiguration getConfig(String fileName) {
		EasyConfig easyConfig = this.configs.get(fileName);

		if (easyConfig == null) {
			easyConfig = new EasyConfig(fileName, this.plugin);
			this.configs.put(fileName, easyConfig);
		}

		return easyConfig.getModifier();
	}

	public void reloadConfig(String fileName) {
		EasyConfig config = configs.get(fileName);

		config.reload();
	}

	public void reloadAll() {
		this.configs.forEach((name, easyConfig) -> {
			easyConfig.reload();
		});

		updateOptionals();
	}
}