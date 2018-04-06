package jeu_base_texte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jeu_base_texte.character.EnemyCharacter;
import jeu_base_texte.character.HelpingCharacter;
import jeu_base_texte.item.EadableItem;
import jeu_base_texte.item.Item;
import jeu_base_texte.objective.GoPlaceObjective;
import jeu_base_texte.objective.Objective;
import jeu_base_texte.padlock.Padlock;
import jeu_base_texte.padlock.PadlockCode;
import jeu_base_texte.padlock.PadlockKey;
import jeu_base_texte.storage.Chest;

public class World {

	private String name;

	private List<Place> places = new ArrayList();

	public World(String name) {
		this.name = name;
	}

	public Place getFirstPlace() {
		return this.places.get(0);
	}

	public void printMap() {
		for (Place place : this.places) {
			for (Map.Entry<String, Exit> entry : place.exits.entrySet()) {
				// String key = entry.getKey();
				Exit value = entry.getValue();
			}
		}
	}

	private void addPlace(Place p) {
		if (!this.places.contains(p)) {
			this.places.add(p);
		} else {
		}
	}

	/* Fonctions qui permettent de complter un World à partir d'un document */

	// Renvoi les éléments enfants en fonction de leur nom
	private List<Node> customGetChildNodeByName(Node node, String name) {
		final NodeList childNodes = node.getChildNodes();
		List<Node> nodes = new ArrayList();
		for (int j = 0; j < childNodes.getLength(); j++) {
			if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE && childNodes.item(j).getNodeName() == name) {
				nodes.add(childNodes.item(j));
			}
		}
		return nodes;
	}

	private Item nodeToItem(Node node, Map<Integer, Item> itemsTemp) {
		Item item = null;
		String itemType = ((Element) node).getAttribute("type");
		String itemName = ((Element) node).getTextContent();
		int itemWeight = Integer.parseInt(((Element) node).getAttribute("weight"));
		String id = ((Element) node).getAttribute("id");

		// recherche du type de l'item
		switch (itemType) {
		case "eadable":
			int itemHealthGain = Integer.parseInt(((Element) node).getAttribute("healthGain"));
			item = new EadableItem(itemName, itemWeight, itemHealthGain);
			break;
		default:
			item = new Item(itemName, itemWeight);
		}

		// recherche d'un id (facultatif)
		if (!id.equals("")) {
			itemsTemp.put(Integer.parseInt(id), item);
		}
		return item;
	}

	private Padlock createPadLock(String type, String padlockOption, Map<Integer, Item> itemsTemp) {
		Padlock padlock = null;
		try {
			switch (type) {
			case "item":
				Item padLockItem;

				if (itemsTemp.containsKey(Integer.parseInt(padlockOption))) {
					padLockItem = itemsTemp.get(Integer.parseInt(padlockOption));
					padlock = new PadlockKey(padLockItem);
				}
				break;
			case "code":
				padlock = new PadlockCode(padlockOption);
				break;
			case "none":
			default:
				break;
			}
		} catch (NumberFormatException e) {

		}

		return padlock;
	}

	public void createWorldByXml(Game game) {

		Map<Integer, Place> placesTemp = new HashMap();
		Map<Integer, Item> itemsTemp = new HashMap();
		Map<String, Chest> idItemPadlockChest = new HashMap();

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File("map.xml"));

			// récupérer l'élément racine (<world>)
			final Element racine = document.getDocumentElement();

			// recupétion de toutes les places (<place>)
			List<Node> objectivesNode = customGetChildNodeByName(racine, "objective");
			List<Node> placesNode = customGetChildNodeByName(racine, "place");
			for (Node i : placesNode) {

				// création de la place
				String namePlace = ((Element) i).getAttribute("name");
				String descriptionPlace = ((Element) i).getAttribute("description");
				if (descriptionPlace.equals(""))
					descriptionPlace = "No description";
				int idPlace = Integer.parseInt(((Element) i).getAttribute("id"));
				Place place = new Place(namePlace, descriptionPlace);
				this.addPlace(place);
				placesTemp.put(idPlace, place);

				// recupération de tous les chests de la place
				List<Node> chestsNode = customGetChildNodeByName(i, "chest");
				for (Node j : chestsNode) {
					String nameChest = ((Element) j).getAttribute("name");
					String padlockType = ((Element) j).getAttribute("padlockType");
					if (padlockType.equals(""))
						padlockType = "null";
					String padlockCode = ((Element) j).getAttribute("optionPadLock");
					if (padlockCode.equals(""))
						padlockCode = "null";

					Chest chest = new Chest(null, nameChest);
					place.chests.put(nameChest.toLowerCase(), chest);
					idItemPadlockChest.put(padlockType + "-" + padlockCode, chest);
					// recuperation de tous les items du chest
					List<Node> itemsNode = customGetChildNodeByName(j, "item");

					for (Node k : itemsNode) {
						chest.add(nodeToItem(k, itemsTemp));
					}
				}

				// ajout des helpersCharacter
				List<Node> helpingcharacterNode = customGetChildNodeByName(i, "helpingcharacter");
				for (Node helpC : helpingcharacterNode) {
					String characterName = ((Element) helpC).getTextContent();
					String characterSpeech = ((Element) helpC).getAttribute("speech");

					place.helpingCharacters.put(characterName.toLowerCase(),
							new HelpingCharacter(characterName, characterSpeech));
				}

				// ajout des enemy
				List<Node> enemyNode = customGetChildNodeByName(i, "enemy");
				for (Node enemy : enemyNode) {
					String enemyName = ((Element) enemy).getTextContent();
					String enemyStrength = ((Element) enemy).getAttribute("strenght");
					String enemyHealth = ((Element) enemy).getAttribute("health");
					try {

						place.enemyCharacters.put(enemyName.toLowerCase(), new EnemyCharacter(enemyName,
								Integer.parseInt(enemyHealth), Integer.parseInt(enemyStrength)));

					} catch (NumberFormatException e) {

					}

				}
				// recuperation de tous les items dans la place
				List<Node> itemsNode = customGetChildNodeByName(i, "item");

				for (Node k : itemsNode) {
					place.storage.add(nodeToItem(k, itemsTemp));
				}
			}
			// ajout des exits
			for (Node i : placesNode) {
				int idPlace = Integer.parseInt(((Element) i).getAttribute("id"));
				if (placesTemp.containsKey(idPlace)) {
					Place place = placesTemp.get(idPlace);
					List<Node> exitsNode = customGetChildNodeByName(i, "exit");
					for (Node e : exitsNode) {
						Exit exit = null;

						int idPlaceExit = Integer.parseInt(((Element) e).getAttribute("idPlace"));

						String padlockType = ((Element) e).getAttribute("padlockType");
						if (padlockType == "")
							padlockType = "none";
						String padlockOption = ((Element) e).getAttribute("optionPadLock");
						if (padlockOption == "")
							padlockOption = "none";

						Padlock padlock = this.createPadLock(padlockType, padlockOption, itemsTemp);

						boolean lock = false;
						if (padlock != null)
							lock = true;

						if (placesTemp.containsKey(idPlaceExit)) {
							Place placeExit = placesTemp.get(idPlaceExit);
							exit = new Exit(placeExit.getName(), placeExit, padlock);
							place.exits.put(placeExit.getName().toLowerCase(), exit);
						} else {
						}

					}
				}
			}

			// ajout des objectifs
			for (Node i : objectivesNode) {
				String type = ((Element) i).getAttribute("type");
				String option = ((Element) i).getAttribute("option");
				String description = ((Element) i).getAttribute("description");
				switch (type) {
				case "goPlace":
					if (placesTemp.containsKey(Integer.parseInt(option))) {

						Place place = placesTemp.get(Integer.parseInt(option));

						Objective objective = new GoPlaceObjective(description, place);
						game.addObjective(objective);
					}

				}
			}
			for (Map.Entry<String, Chest> e : idItemPadlockChest.entrySet()) {
				String key = e.getKey();
				String[] arrEntry = key.split("-");
				Chest chest = e.getValue();

				chest.setPadlock(createPadLock(arrEntry[0], arrEntry[1], itemsTemp));

			}

			for (Map.Entry<Integer, Place> e : placesTemp.entrySet()) {
				Integer key = e.getKey();

				Place value = e.getValue();
			}

		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

}