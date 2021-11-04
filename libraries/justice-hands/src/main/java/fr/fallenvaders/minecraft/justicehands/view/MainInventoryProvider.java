package fr.fallenvaders.minecraft.justicehands.view;

import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.GuiInventoryController;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionCategoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventoryItem;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventoryItemLocation;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Main inventory provider class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class MainInventoryProvider implements InventoryProvider {

  private static final String GUI_INVENTORY_ID = "main";
  private static final String PLAYER_HEAD_ITEM_ID = "player-head";
  private static final String CATEGORY_ITEM_ID = "category";

  private final ComponentHelper componentHelper;
  private final GuiInventory guiInventory;
  private final Logger logger;
  private final SanctionCategoryController sanctionCategoryController;
  private final ViewUtils viewUtils;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param logger The {@link Logger}.
   * @param sanctionCategoryController The {@link SanctionCategoryController}.
   * @param viewUtils The {@link ViewUtils}.
   */
  @Inject
  public MainInventoryProvider(
      @NotNull ComponentHelper componentHelper,
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull Logger logger,
      @NotNull SanctionCategoryController sanctionCategoryController,
      @NotNull ViewUtils viewUtils)
      throws JusticeHandsException {
    this.componentHelper = componentHelper;
    this.guiInventory = guiInventoryController.getInventory(GUI_INVENTORY_ID);
    this.logger = logger;
    this.sanctionCategoryController = sanctionCategoryController;
    this.viewUtils = viewUtils;
  }

  @Override
  public void init(@NotNull Player moderator, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();

    try {
      Player inventoryOwner = Bukkit.getPlayer(UUID.fromString(inventory.getId()));
      if (inventoryOwner == null) {
        throw new JusticeHandsException("No player owner of the GUI inventory found.");
      }
      setPlayerHead(inventoryOwner, contents);
      setCategories(moderator, inventoryOwner, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load main GUI inventory.", e);
      // TODO: notification to player
    }
  }

  @Override
  public void update(@NotNull Player player, @NotNull InventoryContents contents) {
    // Nothing to do
  }

  private @NotNull ItemStack getCategoryItem(@NotNull SanctionCategory sanctionCategory) {
    GuiInventoryItem categoryGuiItem =
        Objects.requireNonNull(guiInventory.getItem(CATEGORY_ITEM_ID).orElse(null));
    ItemStack categoryItem = Objects.requireNonNull(categoryGuiItem.item());

    ItemMeta meta = categoryItem.getItemMeta();
    meta.displayName(componentHelper.getComponent("§4Catégorie: §c" + sanctionCategory.name()));

    List<String> strLore = new ArrayList<>(2);
    strLore.add("");
    strLore.add(sanctionCategory.description());
    List<Component> lore =
        strLore.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    meta.lore(lore);

    categoryItem.setItemMeta(meta);
    return categoryItem;
  }

  private void setPlayerHead(@NotNull Player inventoryOwner, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    GuiInventoryItem item =
        guiInventory
            .getItem(PLAYER_HEAD_ITEM_ID)
            .orElseThrow(() -> new JusticeHandsException("Failed to create the GUI inventory."));
    GuiInventoryItemLocation location = Objects.requireNonNull(item.location());
    ItemStack playerHead = viewUtils.getHead(inventoryOwner);

    contents.set(location.line(), location.column(), ClickableItem.empty(playerHead));
  }

  private void setCategories(
      @NotNull Player moderator,
      @NotNull Player inventoryOwner,
      @NotNull InventoryContents contents)
      throws JusticeHandsException {
    for (SanctionCategory sanctionCategory : sanctionCategoryController.getCategories()) {
      contents.set(
          0,
          0,
          ClickableItem.of(
              getCategoryItem(sanctionCategory),
              e -> {
                if (e.isLeftClick() || e.isRightClick() || e.isShiftClick()) {
                  InventoryBuilderSM.openCategoryMenu(sanctionCategory, moderator, inventoryOwner);
                }
              }));
    }
  }
}
