/*
 * MIT License
 *
 * Copyright (c) 2018 Andavin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package games.bevs.library.commons.reflection;

import org.bukkit.Bukkit;

import java.lang.reflect.*;
import java.util.*;

public final class Reflection {

    /**
     * A general version number for current spigot versions.
     * This corresponds to the {@link #VERSION_NUMBER} and can
     * be used in a greater or less than usage. For example,
     * to see if a version is below 1.11 (i.e. 1.10 or below)
     * <pre>
     *     if (Reflection.VERSION_NUMBER &lt; Reflection.v1_11) {
     *         // Do something
     *     }
     * </pre>
     * This is just a reference point as to not have annoying
     * bugs that are simply from the version number being
     * incorrectly interpreted.
     * <p>
     * These should not be used to see if a version is equal
     * to ({@code Reflection.VERSION_NUMBER == Reflection.v1_11})
     * as it will most likely fail. Nor should it be assumed that
     * because a version number is higher than {@link #v1_11}
     * that it means the version is 1.12.
     * <pre>
     *     if (Reflection.VERSION_NUMBER &gt; Reflection.v1_11) {
     *         // Could still be 1.11
     *     }
     * </pre>
     * Instead use a 1.12 comparision
     * <pre>
     *     if (Reflection.VERSION_NUMBER &gt;= Reflection.v1_12) {
     *         // Is 1.12
     *     }
     * </pre>
     */
    public static final int v1_13 = 1130, v1_12 = 1120, v1_11 = 1110, v1_10 = 1100, v1_9 = 190, v1_8_8 = 183, v1_8 = 180;

    /**
     * The version string that makes up part of CraftBukkit or MinecraftServer imports.
     */
    public static final String VERSION_STRING = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * The version number. 170 for 1_7_R0, 181 for 1_8_R1, etc.
     */
    public static final int VERSION_NUMBER = Integer.parseInt(VERSION_STRING.replaceAll("[v_R]", ""));

    /**
     * The prefix for all NMS packages (e.g. net.minecraft.server.version.).
     */
    public static final String NMS_PREFIX = "net.minecraft.server." + VERSION_STRING + '.';

    /**
     * The prefix for all Craftbukkit packages (e.g. org.bukkit.craftbukkit.version.).
     */
    public static final String CRAFT_PREFIX = "org.bukkit.craftbukkit." + VERSION_STRING + '.';

    private static final ClassResolver CLASS_RESOLVER = /*isAtLeastJava9() ?
            new ModernClassResolver() :*/ new LegacyClassResolver();
    private static final Map<Class<?>, Class<?>> PRIMITIVES = new HashMap<>(10);

    static {
        PRIMITIVES.put(Byte.class, Byte.TYPE);
        PRIMITIVES.put(Short.class, Short.TYPE);
        PRIMITIVES.put(Integer.class, Integer.TYPE);
        PRIMITIVES.put(Long.class, Long.TYPE);
        PRIMITIVES.put(Float.class, Float.TYPE);
        PRIMITIVES.put(Double.class, Double.TYPE);
        PRIMITIVES.put(Boolean.class, Boolean.TYPE);
        PRIMITIVES.put(Void.class, Void.TYPE);
    }

    /**
     * Get an instance of the specified class object optionally
     * getting the object even if the access is denied.
     * <br>
     * If the object is not accessible and access is set to false
     * or an exception is thrown this will return null.
     *
     * @param clazz The class to get an instance of.
     * @param params The parameters to pass into the constructor method.
     * @param <T> The object type to get the instance for.
     * @return A new instance of the given class or null if it is not possible.
     * @deprecated Use {@link #newInstance(Class, Object...)}
     */
    @Deprecated
    public static <T> T getInstance(Class<T> clazz, Object... params) {
        return newInstance(clazz, params);
    }

    /**
     * Get an instance of the class with the given constructor
     * using the given parameters. Optionally getting the instance
     * whether the constructor is accessible or not.
     *
     * @param con The constructor of the object class.
     * @param params The object parameters to pass to the constructor.
     * @param <T> The type of object to retrieve.
     * @return The object type of the given constructor.
     * @deprecated Use {@link #newInstance(Constructor, Object...)}
     */
    @Deprecated
    public static <T> T getInstance(Constructor<T> con, Object... params) {
        return newInstance(con, params);
    }

    /**
     * Get a new instance of the specified class object optionally
     * getting the object even if the access is denied.
     * <br>
     * If the object is not accessible and access is set to false
     * or an exception is thrown this will return null.
     *
     * @param clazz The class to get an instance of.
     * @param params The parameters to pass into the constructor method.
     * @param <T> The object type to get the instance for.
     * @return A new instance of the given class or null if it is not possible.
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) {

        if (params.length == 0) {

            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        Constructor<T> con = findConstructor(clazz, getClassesForObjects(params));
        return con == null ? null : newInstance(con, params);
    }

    /**
     * Get a new instance of the class with the given constructor
     * using the given parameters. Optionally getting the instance
     * whether the constructor is accessible or not.
     *
     * @param con The constructor of the object class.
     * @param params The object parameters to pass to the constructor.
     * @param <T> The type of object to retrieve.
     * @return The object type of the given constructor.
     */
    public static <T> T newInstance(Constructor<T> con, Object... params) {

        if (!con.isAccessible()) {
            con.setAccessible(true);
        }

        try {
            return con.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the value of a field inside of the specified object's
     * class with the given name and casting it to the given type.
     * If the type of the field is different then the return type T
     * then a {@link ClassCastException} will be thrown.
     *
     * @param clazz The class type that the field belongs to.
     * @param instance The instance of the class to get the field for.
     * @param name The name of the field to get.
     * @param <T> The declaration type of the field.
     * @return The value of the given field or null if none exists.
     */
    public static <T> T getValue(Class<?> clazz, Object instance, String name) {
        return getValue(findField(clazz, name), instance);
    }

    /**
     * Get the value of the given field using the given object
     * as an instance to access it.
     *
     * @param field The field to get the value of.
     * @param instance The instance of the class to get the field for.
     * @param <T> The declaration type of the field.
     * @return The value of the given field or null if none exists.
     */
    // May need to validate the generic return type T by taking a Class<T> as parameter
    public static <T> T getValue(Field field, Object instance) {

        if (field == null) {
            return null;
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        T value = null;
        try {
            value = (T) field.get(instance);
            // For now catch the ClassCastException
        } catch (ClassCastException | IllegalAccessException e) {
           e.printStackTrace();
        }

        return value;
    }

    /**
     * Set the value of the field with the given name inside of
     * the specified class and optionally in the instance of the
     * given object. If there is no field with the given name this
     * method will do nothing.
     *
     * @param clazz The class type that the field belongs to.
     * @param instance The instance of the class to set the field for.
     * @param name The name of the field to set the value of.
     * @param value The value to give the field.
     */
    public static void setValue(Class<?> clazz, Object instance, String name, Object value) {
        setValue(findField(clazz, name), instance, value);
    }

    /**
     * Set a value to the given field providing the object instance
     * and the value to set.
     *
     * @param field The field to set the value to.
     * @param instance The instance of the class to set the field for.
     * @param value The value to give the field.
     */
    public static void setValue(Field field, Object instance, Object value) {

        if (field != null) {

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the field with the specified name whether it is
     * accessible or not. If there is no field with the specified
     * name or if the field is in a parent class in the hierarchy
     * and is inaccessible then this method will return null.
     *
     * @param clazz The class the field belongs to.
     * @param name The name of the field.
     * @return The field or null if no field exists.
     * @deprecated Use {@link #findField(Class, String)}
     */
    @Deprecated
    public static Field getField(Class<?> clazz, String name) {
        return findField(clazz, name);
    }

    /**
     * Get the field with the specified name whether it is
     * accessible or not. If there is no field with the specified
     * name or if the field is in a parent class in the hierarchy
     * and is inaccessible then this method will return null.
     *
     * @param clazz The class the field belongs to.
     * @param name The name of the field.
     * @return The field or null if no field exists.
     */
    public static Field findField(Class<?> clazz, String name) {

        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ignored) {
        }

        try {

            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                return superClazz.getField(name);
            }
        } catch (NoSuchFieldException ignored) {
        }

        for (Field field : clazz.getDeclaredFields()) {

            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

    /**
     * Find a field by its type and its {@link Modifier modifiers}
     * specified in the given {@link FieldMatcher} parameters.
     * <p>
     * Note that this method will <i>not</i> include any fields from
     * any super classes of the given class to search; it will only
     * include fields of all access from within the class itself.
     *
     * @param clazz The class that should be searched for the field.
     * @param matcher The {@link FieldMatcher} to use to match fields
     *                in the class.
     * @return The first field found that matches all the required parameters
     *         or {@code null} if no field was found that matched.
     */
    public static Field findField(Class<?> clazz, FieldMatcher matcher) {
        return findField(clazz, 0, matcher);
    }

    /**
     * Find a field by its type and its {@link Modifier modifiers}
     * specified in the given {@link FieldMatcher} parameters.
     * <p>
     * If there are multiple fields that successfully match the
     * {@link FieldMatcher} given, then the index can be used to
     * retrieve the desired field.
     * <p>
     * Note that this method will <i>not</i> include any fields from
     * any super classes of the given class to search; it will only
     * include fields of all access from within the class itself.
     *
     * @param clazz The class that should be searched for the field.
     * @param index The index of field to retrieve. For example,
     *              {@code 0} should be given to retrieve the first
     *              field that matches the parameters.
     * @param matcher The {@link FieldMatcher} to use to match fields
     *                in the class.
     * @return The field found that matches all the required parameters
     *         or {@code null} if no field was found that matched.
     * @throws IndexOutOfBoundsException If there are not enough fields
     *                                   that match the parameters in order
     *                                   to reach the required index.
     */
    public static Field findField(Class<?> clazz, int index, FieldMatcher matcher) throws IndexOutOfBoundsException {

        int found = 0;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            if (matcher.match(field) && found++ == index) {
                return field;
            }
        }

        if (found < index) {
            throw new IndexOutOfBoundsException("Too few matching fields to reach " +
                    index + " in " + clazz.getSimpleName());
        }

        return null;
    }

    /**
     * Invoke a method from the given class with the given name
     * and matching the types of the parameters given returning
     * the type given. If the type given is not the type of the
     * return value of the method found then a {@link ClassCastException}
     * will be thrown.
     *
     * @param clazz The class that the method belong to.
     * @param instance The instance to invoke the method on.
     * @param name The name of the method to invoke.
     * @param params The parameters to pass to the method.
     * @param <T> The method return type (if different an exception will be thrown).
     * @return The value that the method returned.
     * @deprecated Use {@link #invoke(Class, Object, String, Object...)}
     */
    @Deprecated
    public static <T> T invokeMethod(Class<?> clazz, Object instance, String name, Object... params) {
        return invoke(findMethod(clazz, name, getClassesForObjects(params)), instance, params);
    }

    /**
     * Invoke the given method on the given object instance and with
     * the given parameters.
     *
     * @param method The method to invoke.
     * @param instance The instance to invoke the method on.
     * @param params The parameters to pass to the method.
     * @param <T> The method return type (if different an exception will be thrown).
     * @return The value that the method returned.
     * @deprecated Use {@link #invoke(Method, Object, Object...)}
     */
    @Deprecated
    public static <T> T invokeMethod(Method method, Object instance, Object... params) {
        return invoke(method, instance, params);
    }

    /**
     * Invoke a method from the given class with the given name
     * and matching the types of the parameters given returning
     * the type given.
     *
     * @param clazz The class that the method belong to.
     * @param instance The instance to invoke the method on.
     * @param name The name of the method to invoke.
     * @param params The parameters to pass to the method.
     * @param <T> The method return type.
     * @return The value that the method returned.
     * @throws ClassCastException If the type given is not the type
     *                            of the return value of the method
     */
    public static <T> T invoke(Class<?> clazz, Object instance, String name, Object... params) throws ClassCastException {
        return invoke(findMethod(clazz, name, getClassesForObjects(params)), instance, params);
    }

    /**
     * Invoke the given method on the given object instance and with
     * the given parameters.
     *
     * @param method The method to invoke.
     * @param instance The instance to invoke the method on.
     * @param params The parameters to pass to the method.
     * @param <T> The method return type.
     * @return The value that the method returned.
     * @throws ClassCastException If the type given is not the type
     *                            of the return value of the method
     */
    // May need to validate the generic return type T by taking a Class<T> as parameter
    public static <T> T invoke(Method method, Object instance, Object... params) throws ClassCastException {

        if (method == null) {
            return null;
        }

        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        try {
            return (T) method.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the method with the specified name whether it is
     * accessible or not. If there is no method with the specified
     * name or if the method is in a parent class in the hierarchy
     * and is inaccessible then this method will return null.
     *
     * @param clazz The class the method belongs to.
     * @param name The name of the method.
     * @param paramTypes The parameter types of the method.
     * @return The method or null if no method exists.
     * @deprecated Use {@link #findMethod(Class, String, Class...)}
     */
    @Deprecated
    public static Method getMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        return findMethod(clazz, name, paramTypes);
    }

    /**
     * Get the method with the specified name whether it is
     * accessible or not. If there is no method with the specified
     * name or if the method is in a parent class in the hierarchy
     * and is inaccessible then this method will return null.
     *
     * @param clazz The class the method belongs to.
     * @param name The name of the method.
     * @param paramTypes The parameter types of the method.
     * @return The method or null if no method exists.
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {

        try {
            return clazz.getDeclaredMethod(name, paramTypes);
        } catch (NoSuchMethodException ignored) {
        }


        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz == null) {
            return null;
        }

        try {
            // No class only methods can be found so search public super class
            return superClazz.getMethod(name, paramTypes);
        } catch (NoSuchMethodException ignored) {
        }

        for (Method method : clazz.getDeclaredMethods()) {

            if (method.getName().equals(name) && method.getParameterCount() == paramTypes.length
                    && compare(method.getParameterTypes(), paramTypes, false)) {
                return method;
            }
        }

        for (Method method : superClazz.getMethods()) {

            if (method.getName().equals(name) && method.getParameterCount() == paramTypes.length
                    && compare(method.getParameterTypes(), paramTypes, false)) {
                return method;
            }
        }

        return null;
    }

    /**
     * Find the first method by the types of its parameters, its return
     * type and its {@link Modifier modifiers} specified in the given
     * {@link MethodMatcher} parameters.
     * <p>
     * Note that this method will <i>not</i> include any methods from
     * any super classes of the given class to search; it will only
     * include methods of all access from within the class itself.
     *
     * @param clazz The class that should be searched for the method.
     * @param matcher The {@link MethodMatcher} to use to match methods
     *                in the class.
     * @return The first method found that matches all the required parameters
     *         or {@code null} if no method was found that matched.
     */
    public static Method findMethod(Class<?> clazz, MethodMatcher matcher) {
        return findMethod(clazz, 0, matcher);
    }

    /**
     * Find a method by the types of its parameters, its return type
     * and its {@link Modifier modifiers} specified in the given
     * {@link MethodMatcher} parameters.
     * <p>
     * If there are multiple methods that successfully match the
     * {@link MethodMatcher} given, then the index can be used to
     * retrieve the desired method.
     * <p>
     * Note that this method will <i>not</i> include any methods from
     * any super classes of the given class to search; it will only
     * include methods of all access from within the class itself.
     *
     * @param clazz The class that should be searched for the method.
     * @param index The index of method to retrieve. For example,
     *              {@code 0} should be given to retrieve the first
     *              method that matches the parameters.
     * @param matcher The {@link MethodMatcher} to use to match methods
     *                in the class.
     * @return The method found that matches all the required parameters
     *         or {@code null} if no method was found that matched.
     * @throws IndexOutOfBoundsException If there are not enough methods
     *                                   that match the parameters in order
     *                                   to reach the required index.
     */
    public static Method findMethod(Class<?> clazz, int index, MethodMatcher matcher) throws IndexOutOfBoundsException {

        int found = 0;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {

            if (matcher.match(method) && found++ == index) {
                return method;
            }
        }

        if (found < index) {
            throw new IndexOutOfBoundsException("Too few matching methods to reach " +
                    index + " in " + clazz.getSimpleName());
        }

        return null;
    }

    /**
     * Get the constructor in the given class, who's parameter
     * types match the parameter types given.
     *
     * @param clazz The class to get the constructor from.
     * @param paramTypes The parameters types to match to.
     * @param <T> The type of the class to retrieve the constructor for.
     * @return The constructor that matches the requested or null if none is found.
     * @deprecated Use {@link #findConstructor(Class, Class[])}
     */
    @Deprecated
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes) {
        return findConstructor(clazz, paramTypes);
    }

    /**
     * Get the constructor in the given class, who's parameter
     * types match the parameter types given.
     *
     * @param clazz The class to get the constructor from.
     * @param paramTypes The parameters types to match to.
     * @param <T> The type of the class to retrieve the constructor for.
     * @return The constructor that matches the requested or null if none is found.
     */
    public static <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>... paramTypes) {

        try {
            return clazz.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            // Do nothing just continue
        }

        // Sometimes we fail to find the constructor due to a type
        // that is assignable from the true type, but not the exact type.
        // Reflection can be stupid in that way sometimes.

        // Therefore, below, we will get each constructor and check it
        // for compatibility ourselves, but widen the restrictions a bit.

        // All constructors in class regardless of accessibility
        for (Constructor<?> con : clazz.getDeclaredConstructors()) {

            // Must have the same amount of parameters
            if (con.getParameterCount() == paramTypes.length && compare(con.getParameterTypes(), paramTypes, false)) {
                // Class<T> is the class we're searching so it's a safe cast
                return (Constructor<T>) con;
            }
        }

        return null;
    }

    /**
     * Get a class in any NMS package omitting the
     * beginning of the canonical name and enter anything
     * following the version package.
     * <p>
     * For example, to get <b>net.minecraft.server.version.PacketPlayOutChat</b>
     * simply input <b>PacketPlayOutChat</b> omitting the
     * <b>net.minecraft.server.version</b>.
     *
     * @param name The name of the class to retrieve.
     * @return The Minecraft class for the given name or null if class was not found.
     * @deprecated Use {@link #findMcClass(String)}
     */
    @Deprecated
    public static Class<?> getMcClass(String name) {
        return findClass(NMS_PREFIX + name);
    }

    /**
     * Get a class in any Craftbukkit package omitting the
     * beginning of the canonical name and enter anything
     * following the version package.
     * <p>
     * For example, to get <b>org.bukkit.craftbukkit.version.CraftServer</b>
     * simply input <b>CraftServer</b> omitting the
     * <b>org.bukkit.craftbukkit.version</b>. In addition, in order
     * get <b>org.bukkit.craftbukkit.version.entity.CraftPlayer</b>
     * simply input <b>entity.CraftPlayer</b>.
     *
     * @param name The name of the class to retrieve.
     * @return The Craftbukkit class for the given name or null if class was not found.
     * @deprecated Use {@link #findCraftClass(String)}
     */
    @Deprecated
    public static Class<?> getCraftClass(String name) {
        return findClass(CRAFT_PREFIX + name);
    }

    /**
     * Get a class in any NMS package omitting the
     * beginning of the canonical name and enter anything
     * following the version package.
     * <p>
     * For example, to get <b>net.minecraft.server.version.PacketPlayOutChat</b>
     * simply input <b>PacketPlayOutChat</b> omitting the
     * <b>net.minecraft.server.version</b>.
     *
     * @param name The name of the class to retrieve.
     * @return The Minecraft class for the given name or null if class was not found.
     */
    public static Class<?> findMcClass(String name) {
        return findClass(NMS_PREFIX + name);
    }

    /**
     * Get a class in any Craftbukkit package omitting the
     * beginning of the canonical name and enter anything
     * following the version package.
     * <p>
     * For example, to get <b>org.bukkit.craftbukkit.version.CraftServer</b>
     * simply input <b>CraftServer</b> omitting the
     * <b>org.bukkit.craftbukkit.version</b>. In addition, in order
     * get <b>org.bukkit.craftbukkit.version.entity.CraftPlayer</b>
     * simply input <b>entity.CraftPlayer</b>.
     *
     * @param name The name of the class to retrieve.
     * @return The Craftbukkit class for the given name or null if class was not found.
     */
    public static Class<?> findCraftClass(String name) {
        return findClass(CRAFT_PREFIX + name);
    }

    /**
     * Get a class of a specific type using a generic type
     * using the exact canonical name of a class.
     * If the class is not the type that is given by the generic,
     * then a {@link ClassCastException} will be thrown.
     *
     * @param name The canonical name of the class to retrieve.
     * @param <T> The type of the class.
     * @return The class with the name or null if the class is not found.
     * @throws ClassCastException If the class is not the type that
     *                            is given by the generic type.
     */
    public static <T> Class<T> findClass(String name) {

        try {
            return (Class<T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get the class that is calling the method that is calling
     * this method. For example, if method {@code foo()} in class
     * {@code A} calls method {@code bar()} in class {@code B}
     * and {@code bar()} calls this method then this will return
     * the class {@code A}.
     *
     * @return The name of the class calling a method.
     */
    public static String getCallerClass() {
        // Start at 1 to get the class that called
        // the method that called this method :P
        return getCallerClass(1, Collections.emptySet());
    }

    /**
     * Get the class that is calling the method that is calling
     * this method. For example, if method {@code foo()} in class
     * {@code A} calls method {@code bar()} in class {@code B}
     * and {@code bar()} calls this method with index of {@code 0}
     * then this will return the class {@code A}.
     *
     * @param index The index (starting at {@code 0}) of the class to
     *              get from the class calling this method. {@code 0}
     *              will return the calling class {@code 1} will return
     *              the class that called that one and so on.
     * @param exclude The classes to skip over when finding the calling class.
     * @return The name of the class index away from calling a method.
     */
    public static String getCallerClass(int index, Class<?>... exclude) {

        Set<String> excluded;
        if (exclude.length != 0) {

            excluded = new HashSet<>((int) (exclude.length / 0.75));
            for (Class<?> clazz : exclude) {
                excluded.add(clazz.getName());
            }
        } else {
            excluded = Collections.emptySet();
        }

        // Add one to exclude the second call in this class
        return getCallerClass(++index, excluded);
    }

    /**
     * Get the class that is calling the method that is calling
     * this method. For example, if method {@code foo()} in class
     * {@code A} calls method {@code bar()} in class {@code B}
     * and {@code bar()} calls this method with index of {@code 0}
     * then this will return the class {@code A}.
     *
     * @param index The index (starting at {@code 0}) of the class to
     *              get from the class calling this method. {@code 0}
     *              will return the calling class {@code 1} will return
     *              the class that called that one and so on.
     * @param excluded The class names to exclude.
     * @return The name of the class index away from calling a method.
     */
    public static String getCallerClass(int index, Set<String> excluded) {
        // Must add one to exclude this class as well
        String name = CLASS_RESOLVER.resolve(++index);
        while (excluded.contains(name)) {
            // Increment to get the next name since the previous was excluded
            name = CLASS_RESOLVER.resolve(++index);
        }

        return name;
    }

    /**
     * Tell whether the parameter types of a method or constructor
     * match the second given parameter types either exactly or somewhere
     * in the hierarchy.
     *
     * @param primary The parameters of the method or constructor.
     * @param secondary The parameter types to compare to.
     * @param exact If the parameters should match exactly or if
     *              primitive to wrapper type conversions should be
     *              considered when comparing Java primitive types.
     * @return Whether the parameters match or not.
     */
    static boolean compare(Class<?>[] primary, Class<?>[] secondary, boolean exact) {

        if (primary.length != secondary.length) {
            return false;
        }

        if (primary.length == 0) {
            return true;
        }

        for (int i = 0; i < primary.length; ++i) {

            Class<?> primaryType = primary[i], secondaryType = secondary[i];
            // If there is anything that does not match then return false
            if (!primaryType.isAssignableFrom(secondaryType)) {
                // Primitives can have mismatch problems sometimes
                if (!exact && (primaryType.isPrimitive() || secondaryType.isPrimitive())) {
                    Class<?> type1 = primaryType.isPrimitive() ? primaryType : PRIMITIVES.get(primaryType);
                    Class<?> type2 = secondaryType.isPrimitive() ? secondaryType : PRIMITIVES.get(secondaryType);
                    return type1 == type2;
                }

                return false;
            }
        }

        return true;
    }

    /**
     * Get all the classes of each object given.
     * If no objects are given this will return an empty array.
     *
     * @param params the parameter objects to get the classes for.
     * @return The class types of each parameter object.
     */
    private static Class<?>[] getClassesForObjects(Object... params) {

        Class<?>[] paramClasses = new Class<?>[params.length];
        for (int i = 0; i < params.length; ++i) {
            Object param = params[i]; // Null check
            paramClasses[i] = param == null ? Void.class : param.getClass();
        }

        return paramClasses;
    }

    private static boolean isAtLeastJava9() {

        String version = System.getProperty("java.version");
        if (version == null) {
            return false;
        }

        int index = version.indexOf('.');
        if (index > 0) {
            version = version.substring(0, index);
        }

        return version.matches("[0-9]{1,8}") && Integer.parseInt(version) >= 9;
    }

    // No instance accessibility
    private Reflection() {
    }
}
