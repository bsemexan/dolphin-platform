/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.server.binding;

import com.canoo.platform.core.functional.Binding;
import com.canoo.dp.impl.remoting.BindingException;
import com.canoo.dp.impl.remoting.PropertyImpl;
import com.canoo.platform.remoting.Property;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.platform.core.ReflectionHelper;
import com.canoo.platform.remoting.server.binding.PropertyBinder;
import com.canoo.platform.remoting.server.binding.Qualifier;
import com.canoo.dp.impl.server.legacy.ServerAttribute;

import java.lang.reflect.Field;

public class PropertyBinderImpl implements PropertyBinder {

    public <T> Binding bind(final Property<T> property, final Qualifier<T> qualifier) {
        Assert.requireNonNull(property, "property");
        Assert.requireNonNull(qualifier, "qualifier");

        if(property instanceof PropertyImpl) {
            try {
                final PropertyImpl p = (PropertyImpl) property;

                final Field attributeField = ReflectionHelper.getInheritedDeclaredField(PropertyImpl.class, "attribute");
                final ServerAttribute attribute = ReflectionHelper.getPrivileged(attributeField, p);
                if(attribute == null) {
                    throw new NullPointerException("attribute == null");
                }
                attribute.setQualifier(qualifier.getIdentifier());
                return new Binding() {
                    @Override
                    public void unbind() {
                        attribute.setQualifier(null);
                    }
                };
            } catch (Exception e) {
                throw new BindingException("Can not bind the given property to the qualifier! Property: " + property + ", qualifier: " + qualifier , e);
            }
        } else {
            throw new BindingException("Can not bind the given property to the qualifier! Property: " + property + ", qualifier: " + qualifier);
        }
    }

}
