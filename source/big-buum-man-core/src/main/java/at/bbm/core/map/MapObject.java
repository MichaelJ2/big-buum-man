package at.bbm.core.map;

import at.bbm.core.GlobalProperties;
import at.bbm.core.utils.FileUtils;
import at.bbm.core.objects.fields.Field;
import at.bbm.core.objects.fields.FieldFactory;
import at.bbm.core.objects.fields.FieldType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This object will hold the map and only
 * has some basic functions like:
 *
 * - load default map
 * - load map of size width x height
 * - load map from file
 */
public class MapObject {

    /** The map as 2-dimensional array of MapField */
    protected volatile MapField[][] map;

    /** List of spawn points to place players on start at them */
    private final List<Location> spawnPoints = new ArrayList<>();

    /** Spawn points for fast access */
    private int spawnPointCount = 0;

    /** Empty constructor for blank map */
    protected MapObject() {
        // do nothing
    }

    /** Constructor to get MapObject from a map file */
    protected MapObject(final File paramFile) {
        this.loadMap(paramFile);
    }

    /** Constructor to get an empty MapObject of a
     * given size filled with ground fields and a border.
     * Map size will be height + 2 border fields and
     * width + 2 border fields
     */
    protected MapObject(final int paramHeight, final int paramWidth) {
        this.loadMap(paramHeight, paramWidth);
    }

    /** Initializes map with empty fields, required! */
    private void initialize() {
        final int width = map[0].length;
        for (int y = 0; y < map.length; y++) {
            this.map[y] = new MapField[width];
            for (int x = 0; x < map[y].length; x++) {
                this.map[y][x] = new MapField();
            }
        }
    }

    /** Will load default map */
    public void loadDefaultMap() {
        this.loadMap(GlobalProperties.MAP_DEFAULT_SIZE, GlobalProperties.MAP_DEFAULT_SIZE);
    }

    /** Load map from map file */
    public boolean loadMap(final File paramFile) {
        boolean result = false;

        this.map = null;

        try {
            final Field[][] fields = FileUtils.INSTANCE.loadMap(paramFile);

            this.map = new MapField[fields.length][fields[0].length];
            this.initialize();

            this.spawnPoints.clear();
            this.spawnPointCount = 0;

            for (int y = 0; y < this.map.length; y++) {
                for (int x = 0; x < this.map[y].length; x++) {
                    final Field field = fields[y][x];
                    this.map[y][x].setField(field);
                    if (FieldType.BORDER.equals(field.getFieldType())) {
                        this.spawnPointCount++;
                        this.spawnPoints.add(new Location(x, y));
                    }
                }
            }
            result = true;
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /** Load mad of given size
     * Map size will be height + 2 border fields and
     * width + 2 border fields
     */
    public void loadMap(final int paramHeight, final int paramWidth) {

        this.spawnPoints.clear();
        this.spawnPointCount = 0;

        this.map = new MapField[paramHeight+2][paramWidth+2];
        this.initialize();

        // first row
        for (int x = 0; x < paramWidth+2; x++) {
            this.map[0][x].setField(FieldFactory.getField(FieldType.BORDER));
        }

        for (int y = 1; y < paramHeight+1; y++) {
            // first field
            this.map[y][0].setField(FieldFactory.getField(FieldType.BORDER));
            // fields inside
            for (int x = 1; x < paramWidth+1; x++) {
                this.map[y][x].setField(FieldFactory.getField(FieldType.GROUND));
            }
            // last field
            this.map[y][this.map[0].length-1].setField(FieldFactory.getField(FieldType.BORDER));
        }

        // last row
        for (int x = 0; x < paramWidth+2; x++) {
            this.map[paramHeight+1][x].setField(FieldFactory.getField(FieldType.BORDER));
        }
    }

    /** For development purpose only! */
    protected void printToCmd() {
        System.out.println();
        for (int y = 0; y < this.map.length; y++) {
            for (int x = 0; x < this.map[0].length; x++) {
                System.out.print(this.map[y][x].getField().getFieldType().ID);
            }
            System.out.println();
        }
        System.out.println();
    }

    /** Get the count of spawn points */
    public int getSPawnPointCount() {
        return this.spawnPointCount;
    }

    /** Get all spawn points */
    public List<Location> getSpawnPoints() {
        return this.spawnPoints;
    }
}
