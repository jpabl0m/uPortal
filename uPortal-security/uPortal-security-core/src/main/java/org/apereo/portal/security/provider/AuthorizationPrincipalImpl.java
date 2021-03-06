/**
 * Licensed to Apereo under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. Apereo
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at the
 * following location:
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apereo.portal.security.provider;

import org.apereo.portal.AuthorizationException;
import org.apereo.portal.groups.IEntityGroup;
import org.apereo.portal.portlet.om.PortletLifecycleState;
import org.apereo.portal.security.IAuthorizationPrincipal;
import org.apereo.portal.security.IAuthorizationService;
import org.apereo.portal.security.IPermission;
import org.apereo.portal.security.IPermissionPolicy;

/** */
public class AuthorizationPrincipalImpl implements IAuthorizationPrincipal {
    private final String key;
    private final Class type;
    private IAuthorizationService authorizationService;
    private String principalString;

    /** Constructor for ReferenceAuthorizationPrincipal. */
    public AuthorizationPrincipalImpl(
            String newKey, Class newType, IAuthorizationService authService) {
        super();
        key = newKey;
        type = newType;
        authorizationService = authService;
        initialize();
    }
    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to manage this channel.
     *
     * @return boolean
     * @param channelPublishId String - the Channel publish ID
     * @exception AuthorizationException thrown when authorization information could not be
     *     retrieved.
     */
    @Override
    public boolean canManage(String channelPublishId) throws AuthorizationException {
        return getAuthorizationService().canPrincipalManage(this, channelPublishId);
    }
    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to publish.
     *
     * @return boolean
     * @exception AuthorizationException thrown when authorization information could not be
     *     retrieved.
     */
    @Override
    public boolean canManage(PortletLifecycleState state, String categoryId)
            throws AuthorizationException {
        return getAuthorizationService().canPrincipalManage(this, state, categoryId);
    }

    @Override
    public boolean canConfigure(String channelPublishId) throws AuthorizationException {
        return getAuthorizationService().canPrincipalConfigure(this, channelPublishId);
    }
    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to render this channel.
     *
     * @return boolean
     * @param channelPublishId int - the Channel publish ID
     * @exception AuthorizationException thrown when authorization information could not be
     *     retrieved.
     */
    @Override
    public boolean canRender(String channelPublishId) throws AuthorizationException {
        return getAuthorizationService().canPrincipalRender(this, channelPublishId);
    }
    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to subscribe to this
     * channel.
     *
     * @return boolean
     * @param channelPublishId int - the Channel publish ID
     * @exception AuthorizationException thrown when authorization information could not be
     *     retrieved.
     */
    @Override
    public boolean canSubscribe(String channelPublishId) throws AuthorizationException {
        return getAuthorizationService().canPrincipalSubscribe(this, channelPublishId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof IAuthorizationPrincipal)) return false;
        IAuthorizationPrincipal other = (IAuthorizationPrincipal) obj;
        if (key == null) {
            if (other.getKey() != null) return false;
        } else if (!key.equals(other.getKey())) return false;
        if (type == null) {
            if (other.getType() != null) return false;
        } else if (!type.equals(other.getType())) return false;
        return true;
    }
    /**
     * Returns the <code>IPermissions</code> for this <code>IAuthorizationPrincipal</code>,
     * including inherited <code>IPermissions</code>.
     *
     * @return org.apereo.portal.security.IPermission[]
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public IPermission[] getAllPermissions() throws AuthorizationException {
        return getAllPermissions(null, null, null);
    }
    /**
     * Returns the <code>IPermissions</code> for this <code>IAuthorizationPrincipal</code> for the
     * specified <code>owner</code>, <code>activity</code> and <code>target</code>. Inherited <code>
     * IPermissions</code> are included. Null parameters are ignored, so <code>
     * getPermissions(null, null, null)</code> should retrieve all <code>IPermissions</code> for an
     * <code>IAuthorizationPrincipal</code>.
     *
     * @return org.apereo.portal.security.IPermission[]
     * @param owner String
     * @param activity String
     * @param target String
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public IPermission[] getAllPermissions(String owner, String activity, String target)
            throws AuthorizationException {
        return getAuthorizationService()
                .getAllPermissionsForPrincipal(this, owner, activity, target);
    }
    /** @return org.apereo.portal.security.IAuthorization */
    IAuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    /** @return String */
    @Override
    public String getKey() {
        return key;
    }
    /**
     * Returns the <code>IPermissions</code> for this <code>IAuthorizationPrincipal</code>.
     *
     * @return org.apereo.portal.security.IPermission[]
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public IPermission[] getPermissions() throws AuthorizationException {
        return getPermissions(null, null, null);
    }
    /**
     * Returns the <code>IPermissions</code> for this <code>IAuthorizationPrincipal</code> for the
     * specified <code>owner</code>, <code>activity</code> and <code>target</code>. Null parameters
     * are ignored, so <code>getPermissions(null, null, null)</code> should retrieve all <code>
     * IPermissions</code> for an <code>IAuthorizationPrincipal</code>.
     *
     * @return org.apereo.portal.security.IPermission[]
     * @param owner String
     * @param activity String
     * @param target String
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public IPermission[] getPermissions(String owner, String activity, String target)
            throws AuthorizationException {
        return getAuthorizationService().getPermissionsForPrincipal(this, owner, activity, target);
    }
    /** @return String */
    @Override
    public String getPrincipalString() {
        return principalString;
    }
    /** @return Class */
    @Override
    public Class getType() {
        return type;
    }

    @Override
    public boolean isGroup() {
        return IEntityGroup.class.equals(type);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to perform the <code>
     * activity</code> on the <code>target</code>. Params <code>owner</code> and <code>activity
     * </code> must be non-null. If <code>target</code> is null, then the target is not checked.
     *
     * @return boolean
     * @param owner String
     * @param activity String
     * @param target String
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public boolean hasPermission(String owner, String activity, String target)
            throws AuthorizationException {
        return getAuthorizationService().doesPrincipalHavePermission(this, owner, activity, target);
    }
    /** Set the value of the principal string. */
    private void initialize() {
        principalString = getAuthorizationService().getPrincipalString(this);
    }

    /**
     * Returns a String that represents the value of this object.
     *
     * @return a string representation of the receiver
     */
    @Override
    public String toString() {
        return getPrincipalString();
    }

    /**
     * Answers if this <code>IAuthorizationPrincipal</code> has permission to perform the <code>
     * activity</code> on the <code>target</code>, as evaluated by the <code>policy</code>. Params
     * <code>policy</code>, <code>owner</code> and <code>activity</code> must be non-null.
     *
     * @return boolean
     * @param owner String
     * @param activity String
     * @param target String
     * @param policy org.apereo.portal.security.IPermissionPolicy
     * @exception AuthorizationException indicates authorization information could not be retrieved.
     */
    @Override
    public boolean hasPermission(
            String owner, String activity, String target, IPermissionPolicy policy)
            throws AuthorizationException {
        return getAuthorizationService()
                .doesPrincipalHavePermission(this, owner, activity, target, policy);
    }
}
