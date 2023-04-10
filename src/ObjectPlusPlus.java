import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ObjectPlusPlus implements Serializable {
    /**
     * Stores information about all connections of this object.
     */
    private Map<String, Map<Object, ObjectPlusPlus>> links = new Hashtable<>();
    protected static Map<String, Integer> roleCardinality = new HashMap<>();
    /**
     * Stores information about all parts connected with any objects.
     */
    private static Set<ObjectPlusPlus> allParts = new HashSet<>();

    /**
     * The constructor.
     *
     */
    protected ObjectPlusPlus() {
        super();
    }



    protected static void setRoleCardinality(String role, Integer cardinality){
        roleCardinality.put(role, cardinality);
    }


    /**
     * Creates a new link (private, utility method).
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param qualifier
     * @param counter
     */
    private void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier, int counter)
            throws Exception {

        // Protection for the reverse connection
        if(counter < 1) {
            return;
        }

        Map<Object, ObjectPlusPlus> objectLinks;
        // Find a collection of links for the role
        if(links.containsKey(roleName)) {
            // Get the links
            objectLinks = links.get(roleName);
        }
        else {
            // No links ==> create them
            objectLinks = new HashMap<>();
            links.put(roleName, objectLinks);
        }

        Map<Object, ObjectPlusPlus> targetObjectLinks;
        // Find a collection of links for the reverse role
        if(targetObject.links.containsKey(reverseRoleName)) {
            // Get the links
            targetObjectLinks = targetObject.links.get(roleName);
        }
        else {
            // No links ==> create them
            targetObjectLinks = new HashMap<>();
            targetObject.links.put(reverseRoleName, targetObjectLinks);
        }

        int currentUsedCardinalityOnThisSide = this.links.get(roleName).size();
        int maximumCardinalityOnThisSide = roleCardinality.get(roleName);
        int currentUsedCardinalityOnOtherSide = targetObject.links.get(reverseRoleName).size();
        int maximumCardinalityOnOtherSide = roleCardinality.get(reverseRoleName);
        if(currentUsedCardinalityOnThisSide >= maximumCardinalityOnThisSide || (currentUsedCardinalityOnOtherSide >= maximumCardinalityOnOtherSide && counter > 1)) throw new Exception("Cardinality error");
        // Check if there is already the connection
        // If yes, then ignore the creation
        if(!objectLinks.containsKey(qualifier)) {
            // Add a link for the target object
            objectLinks.put(qualifier, targetObject);

            // Add the reverse connection
            targetObject.addLink(reverseRoleName, roleName, this, this, counter - 1);
        }
    }

    private void removeLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier, int counter){
        Map<Object, ObjectPlusPlus> objectLinks;        // Protection for the reverse connection
        if(counter < 1) {
            return;
        }
        if(links.containsKey(roleName)) {
            // Get the links
            objectLinks = links.get(roleName);
            if(!objectLinks.containsKey(qualifier)) {
                // Add a link for the target object
                objectLinks.remove(qualifier, targetObject);

                // Add the reverse connection
                targetObject.removeLink(reverseRoleName, roleName, this, this, counter - 1);
            }
        }
    }

    /**
     * Creates a new link to the given target object (optionally as quilified connection).
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param qualifier
     */
    protected void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier)
            throws Exception {
        addLink(roleName, reverseRoleName, targetObject, qualifier, 2);
    }
    protected void removeLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier) {
        removeLink(roleName, reverseRoleName, targetObject, qualifier, 2);
    }

    /**
     * Creates a new link to the given target object (as an ordinary association, not the quilified one).
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    protected void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) throws Exception {
        addLink(roleName, reverseRoleName, targetObject, targetObject);
    }
    protected void removeLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        removeLink(roleName, reverseRoleName, targetObject, targetObject);
    }

    /**
     * Adds an information about a connection (using a "semi" composition).
     * @param roleName
     * @param reverseRoleName
     * @throws Exception
     */
    protected void addPart(String roleName, String reverseRoleName, ObjectPlusPlus partObject) throws Exception {
        // Check if the part exist somewhere
        if(allParts.contains(partObject)) {
            throw new Exception("The part is already connected to a whole!");
        }

        addLink(roleName, reverseRoleName, partObject);

        // Store adding the object as a part
        allParts.add(partObject);
    }
    protected void removePart(String roleName, String reverseRoleName, ObjectPlusPlus partObject) throws Exception {
        // Check if the part exist somewhere
        if(!allParts.contains(partObject)) {
            throw new Exception("The part is not connected to a whole!");
        }

        removeLink(roleName, reverseRoleName, partObject);

        // Store adding the object as a part
        allParts.remove(partObject);
    }

    /**
     * Gets an array of connected objects for the given role name.
     * @param roleName
     * @return
     * @throws Exception
     */
    protected List<ObjectPlusPlus> getLinks(String roleName) throws Exception {
        Map<Object, ObjectPlusPlus> objectLinks;

        if(!links.containsKey(roleName)) {
            // No links for the role
            throw new Exception("No links for the role: " + roleName);
        }

        objectLinks = links.get(roleName);

        return new LinkedList<ObjectPlusPlus>(objectLinks.values());
    }

    protected <T> List<T> getLinksInType(String roleName) throws Exception {
        try {
            List<ObjectPlusPlus> resultList = this.getLinks(roleName);
            List<T> castedList = new LinkedList<>();
            for (ObjectPlusPlus object:
                    resultList) {
                castedList.add((T) object);
            }
            return castedList;
        }
        catch (Exception ex){
            return null;
        }
    }

    /**
     * Shows links to the given stream.
     * @param roleName
     * @param stream
     * @throws Exception
     */
    protected void showLinks(String roleName, PrintStream stream) throws Exception {
        Map<Object, ObjectPlusPlus> objectLinks;

        if(!links.containsKey(roleName)) {
            // No links
            throw new Exception("No links for the role: " + roleName);
        }

        objectLinks = links.get(roleName);


        stream.println(this.getClass().getSimpleName() + " links, role '" + roleName + "':");


        for(Object key : objectLinks.keySet()){
            Object obj = objectLinks.get(key);
            if(obj != key){
                stream.println("   Qualifier: " + key + " ---> " + obj);
            }
            else{
                stream.println("   " + obj);
            }
        }
    }

    /**
     * Gets an object for the given qualifier (a qualified association).
     * @param roleName
     * @param qualifier
     * @return
     * @throws Exception
     */
    protected ObjectPlusPlus getLinkedObject(String roleName, Object qualifier) throws Exception {
        Map<Object, ObjectPlusPlus> objectLinks;

        if(!links.containsKey(roleName)) {
            // No links
            throw new Exception("No links for the role: " + roleName);
        }

        objectLinks = links.get(roleName);
        if(!objectLinks.containsKey(qualifier)) {
            // No link for the qualifer
            throw new Exception("No link for the qualifer: " + qualifier);
        }

        return objectLinks.get(qualifier);
    }

    /**
     * Checks if there are any links for the given role name.
     * @param nazwaRoli
     * @return
     */
    protected boolean anyLink(String nazwaRoli) {
        if(!links.containsKey(nazwaRoli)) {
            return false;
        }

        Map<Object, ObjectPlusPlus> links = this.links.get(nazwaRoli);
        return links.size() > 0;
    }

    /**
     * Checks if there is a link to a given object as a given role.
     * @param roleName
     * @param targetObject
     * @return
     */
    protected boolean isLink(String roleName, ObjectPlusPlus targetObject) {
        Map<Object, ObjectPlusPlus> objectLink;

        if(!links.containsKey(roleName)) {
            // No links for the role
            return false;
        }

        objectLink = links.get(roleName);

        return objectLink.containsValue(targetObject);
    }

    protected Object getQualifier(String roleName, ObjectPlusPlus targetObject){
        for(Object qualifier : this.links.get(roleName).keySet()){
            if(this.links.get(roleName).get(qualifier) == targetObject){
                return qualifier;
            }
        }
        return null;
    }


    //UTILITY METHODS
    /**
     * Inverts maps.
     * @param map
     * @return
     */
    public static <V, K> Map<V, K> invertMapUsingStreams(Map<K, V> map) {
        Map<V, K> inversedMap = map.entrySet()
                                   .stream()
                                   .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
        return inversedMap;
    }
}
