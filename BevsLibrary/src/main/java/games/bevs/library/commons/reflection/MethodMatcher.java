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


import games.bevs.library.commons.Console;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static games.bevs.library.commons.reflection.Reflection.compare;


/**
 * An attribute matcher that takes in all attributes
 * of a method to match to a specific method in a class.
 *
 * @since November 03, 2018
 * @author Andavin
 */
public class MethodMatcher extends AttributeMatcher<Method, MethodMatcher> {

    private static final int BRIDGE = 0x40;
    private final Class<?>[] parametersTypes;
    private boolean requireExactMatch;

    /**
     * Create a new matcher to find a method.
     *
     * @param returnType The {@link Class return type} of
     *                   the method to match to.
     * @param parametersTypes The types of the parameters for the
     *                        method to match to in order of how
     *                        they are declared on the method.
     */
    public MethodMatcher(Class<?> returnType, Class<?>... parametersTypes) {
        super(returnType, Modifier.methodModifiers() | BRIDGE);
        this.parametersTypes = parametersTypes;
    }

    /**
     * Require that the method be a {@code bridge} method
     * that was created by the compiler.
     *
     * @return This attribute matcher.
     */
    public MethodMatcher requireBridge() {

        this.requiredModifiers |= BRIDGE;
        if ((this.disallowedModifiers & BRIDGE) != 0) {
            Console.log("Reflection", "Bridge is both required and disallowed.");
        }

        return this;
    }

    /**
     * Disallow that the method may be a {@code bridge} method
     * that was created by the compiler.
     *
     * @return This attribute matcher.
     */
    public MethodMatcher disallowBridge() {

        this.disallowedModifiers |= BRIDGE;
        if ((this.requiredModifiers & BRIDGE) != 0) {
            Console.log("Reflection", "Bridge is both required and disallowed.");
        }

        return this;
    }

    /**
     * Require that the method match all parameters provided
     * here exactly. For example, if {@link Byte} is provided
     * as a parameter type, then a method that takes a parameter
     * of type {@code byte} will not be a match.
     *
     * @return This attribute matcher.
     */
    public MethodMatcher requireExactMatch() {
        this.requireExactMatch = true;
        return this;
    }

    @Override
    boolean match(Method method) {
        return this.match(method.getModifiers(), method.getReturnType()) &&
                compare(method.getParameterTypes(), this.parametersTypes, this.requireExactMatch);
    }
}
