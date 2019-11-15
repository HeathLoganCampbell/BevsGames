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

package games.bevs.library.commons.utils.reflection;

import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * @since October 31, 2018
 * @author Andavin
 */
public class LegacyClassResolver implements ClassResolver {

    private static final int DEPTH_ADDITION = 3;
    private final boolean hasSunReflection;
    private final Method stackTraceMethod;

    LegacyClassResolver() {
        hasSunReflection = hasSunReflection();
        stackTraceMethod = getStackTraceMethod();
    }

    @Override
    public String resolve(int depth) {

        if (hasSunReflection) {
            try {
                //noinspection deprecation
                return sun.reflect.Reflection.getCallerClass(depth + DEPTH_ADDITION).getName();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to get caller class from sun.reflect.Reflection", e);
            }
        }

        Throwable throwable = new Throwable();
        if (stackTraceMethod != null) {

            try {
                return Reflection.<StackTraceElement>invoke(this.stackTraceMethod,
                        throwable, depth + DEPTH_ADDITION).getClassName();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to get single stack trace element from throwable", e);
            }
        }

        StackTraceElement[] trace = throwable.getStackTrace();
        return trace[Math.min(depth + DEPTH_ADDITION, trace.length - 1)].getClassName();
    }

    private static boolean hasSunReflection() {
        try {
            //noinspection deprecation
            Class<?> clazz = sun.reflect.Reflection.getCallerClass(1);
            return LegacyClassResolver.class.equals(clazz);
        } catch (Throwable throwable) {
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static Method getStackTraceMethod() {
        Method method = Reflection.findMethod(Throwable.class, "getStackTraceElement", int.class);
        StackTraceElement element = Reflection.invoke(method, new Throwable(), 0);
        return LegacyClassResolver.class.getName().equals(element.getClassName()) ? method : null;
    }
}
