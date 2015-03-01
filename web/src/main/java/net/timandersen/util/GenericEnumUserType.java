package net.timandersen.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class GenericEnumUserType<E extends Enum<E>> implements UserType, ParameterizedType {

    protected Class<? extends Enum> enumClass;
    protected Class<?> identifierType;
    protected String valueOfMethodName;
    protected String identifierMethodName;
    protected Method identifierMethod;
    protected Method valueOfMethod;
    protected AbstractStandardBasicType<?> type;
    private int[] sqlTypes;

    public GenericEnumUserType(Class<? extends Enum> enumClass, String valueOfMethodName, String identifierMethodName) {
        this.enumClass = enumClass;
        this.valueOfMethodName = valueOfMethodName;
        this.identifierMethodName = identifierMethodName;
    }

    public void setParameterValues(Properties parameters) {
        try {
            identifierMethod = enumClass.getMethod(identifierMethodName);
            identifierType = identifierMethod.getReturnType();
        } catch (Exception e) {
            throw new HibernateException("Failed to obtain identifier method named '" + identifierMethodName
                    + "' in class '" + enumClass.getName() + "'", e);
        }

        type = (AbstractSingleColumnStandardBasicType<?>) new TypeResolver().basic(identifierType.getName());

        if (type == null) throw new HibernateException("Unsupported identifier type " + identifierType.getName());

        sqlTypes = new int[]{((AbstractSingleColumnStandardBasicType<?>) type).sqlType()};

        try {
            valueOfMethod = enumClass.getMethod(valueOfMethodName, identifierType);
        } catch (Exception e) {
            throw new HibernateException(
                    "Failed to obtain a method named '" + valueOfMethodName + "' which accepts and argument of type '"
                            + identifierType + "' in class '" + enumClass.getName() + "'", e);
        }
    }

    public Class returnedClass() {
        return enumClass;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object o) throws HibernateException, SQLException {

        Object identifier = type.get(rs, names[0], session);
        if (rs.wasNull()) {
            return null;
        }

        try {
            return valueOfMethod.invoke(enumClass, identifier);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Exception while invoking valueOf method '" + valueOfMethod.getName() + "' of " +
                    "enumeration class '" + enumClass + "'", e);
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
            if (value == null) {
                st.setNull(index, ((AbstractSingleColumnStandardBasicType<?>) type).sqlType());
            } else {
                Object identifier = identifierMethod.invoke(value);
                type.nullSafeSet(st, identifier, index, session);
            }
        } catch (Exception e) {
            throw new HibernateException("Exception while invoking identifierMethod '" + identifierMethod.getName() + "' of " +
                    "enumeration class '" + enumClass + "'", e);
        }
    }

    public int[] sqlTypes() {
        return sqlTypes.clone();
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x != null && x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}