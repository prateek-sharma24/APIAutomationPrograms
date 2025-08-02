package org.example.ex_07_Payload_management.Classes.tools;

import java.util.LinkedHashMap;
import java.util.Map;



public class Booking {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private Bookingdates bookingdates;
    private String additionalneeds;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public Bookingdates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(Bookingdates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
Use this tool offline:Maven pluginGradle pluginAnt taskCLIJava API
        Reference

properties
For each property present in the 'properties' definition, we add a property to a given Java class according to the JavaBeans spec. A private field is added to the parent class, along with accompanying accessor methods (getter and setter).

E.g. json schema

    {
            "type" : "object",
            "properties" : {
            "foo" : {
            "type" : "string"
            }
            }
            }
resulting Java type:


public class MyObject {
    private String foo;
    public String getFoo() {
        return foo;
    }
    public void setFoo(String foo) {
        this.foo = foo;
    }
}
If the generate-builders property is set to true, then a builder method is also added:

public MyObject withFoo(String foo) {
    this.foo = foo;
    return this;
}

These builder methods allow easy, one-liner construction and initialization of objects, like:

MyObject o = new MyObject().withFoo("foo").withBar("bar").withBaz("baz");
type
When encountering the type attribute (e.g. for properties), jsonschema2pojo maps schema types as follows:

Schema type	Java type
string	java.lang.String
number	java.lang.Double
integer	java.lang.Integer
boolean	java.lang.Boolean
object	generated Java type
array	java.util.List
array (with "uniqueItems":true)	java.util.Set
null	java.lang.Object
any	java.lang.Object
When applying the usePrimitives option, the primitives double, integer and boolean will replace the wrapper types listed above.


additionalProperties
If additionalProperties is specified and set to the boolean value false, then the generated Java type does not support additional properties.

If the additionalProperties node is undefined (not present), null or empty, then a new bean property named "additionalProperties", of type Map<String,Object> is added to the generated type (with appropriate accessors). The accessors are annotated to allow Jackson to marshal/unmarshal unrecognised (additional) properties found in JSON data from/to this map.

        So, schema file myObject.json like:

        {
        "type" : "object"
        }
or

{
    "type" : "object",
        "additionalProperties" : {}
}
produces:

public class MyObject {

    private java.util.Map<String, Object> additionalProperties = new java.util.HashMap<String, Object>();

    @org.codehaus.jackson.annotate.JsonAnyGetter
    public java.util.Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @org.codehaus.jackson.annotate.JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
If the additionalProperties node is present and specifies a schema, then an "additionalProperties" map is added to the generated type and map values will be restricted according to the additionalProperties schema.

So, schema file myObject.json like:

        {
        "type" : "object",
        "additionalProperties" : {
        "type" : "number"
        }
        }
produces:

public class MyObject {

    private java.util.Map<String, Double> additionalProperties = new java.util.HashMap<String, Double>();

    @org.codehaus.jackson.annotate.JsonAnyGetter
    public java.util.Map<String, Double> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @org.codehaus.jackson.annotate.JsonAnySetter
    public void setAdditionalProperties(String name, Double value) {
        this.additionalProperties.put(name, value);
    }

}
Where the additionalProperties schema species a type object, map values will be restricted to instances of a newly generated Java type. If the given schema does not specify the javaType property, the name of the newly generated type will be derived from the parent type name and the suffix 'Property'.

So, schema file myObject.json like:

        {
        "type" : "object",
        "additionalProperties" : {
        "type" : "object"
        }
        }
produces:

public class MyObject {

    private java.util.Map<String, MyObjectProperty> additionalProperties = new java.util.HashMap<String, MyObjectProperty>();

    @org.codehaus.jackson.annotate.JsonAnyGetter
    public java.util.Map<String, MyObjectProperty> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @org.codehaus.jackson.annotate.JsonAnySetter
    public void setAdditionalProperties(String name, MyObjectProperty value) {
        this.additionalProperties.put(name, value);
    }

}
items
The 'items' rule defines a schema for the contents of an array. In generated Java types, the value of 'items' dictates the generic type of Lists and Sets.

So, this example JSON Schema:

        {
        "type" : "object",
        "properties" : {
        "myArrayProperty" : {
        "type" : "array",
        "items" : {
        "type" : "string"
        }
        }
        }
        }
produces a property called myArrayProperty of type List<String> in the generated Java type. If items itself declares a complex type ("type" : "object") then the generic type of the List or Set will itself be a generated type e.g. List<MyComplexType>.

        required
The 'required' schema rule doesn't produce a structural change in generated Java types, it simply causes the text (Required) to be added to the JavaDoc for fields, getters and setters.

optional
The 'optional' schema rule doesn't produce a structural change in generated Java types, it simply causes the text (Optional) to be added to the JavaDoc for fields, getters and setters.

This schema rule is deprecated since Draft 03 of the JSON Schema specification. Rather than marking optional properties as optional, one should mark required properties as required.

        uniqueItems
For properties of type 'array', setting uniqueItems to false (or omitting it entirely) causes the generated Java property to be of type java.util.List.

When uniqueItems is set to true, the generated Java property value is of type java.util.Set.

enum
When jsonschema2pojo encounters JSON Schema declarations of type "enum" it generates a Java enum type.

When a generated type includes a property of type "enum", the generated enum type becomes a static inner type declared within the enclosing (parent) generated type. If an enum is declared at the root of a schema, the generated enum is a public Java type with no enclosing type.

The actual enum value is held in a 'value' property inside the enum constants. The generated enum type also includes annotations that allow Jackson to correctly marshal/unmarshal JSON values, even when the actual values contain spaces, start with digits, or contain other characters that cannot legally form part of the Java enum constant name.

So, if we declare a schema myObject.json with an enum property:

        {
        "type" : "object",
        "properties" : {
        "myEnum" : {
        "type" : "string",
        "enum" : ["one", "secondOne", "3rd one"]
        }
        }
        }
we see a generated MyObject Java type with an inner enum type like:

@Generated("com.googlecode.jsonschema2pojo")
public static enum MyEnum {

    ONE("one"),
    SECOND_ONE("secondOne"),
    _3_RD_ONE("3rd one");
    private final String value;

    private MyEnum(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static MyObject.MyEnum fromValue(String value) {
        for (MyObject.MyEnum c: MyObject.MyEnum.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
default
Using the default rule in your JSON Schema causes the corresponding property in your generated Java type to be initialised with a default value. You'll see the default value is assigned during field declaration.

Default values are supported for the JSON Schema properties of type string, integer, number and boolean; for enum properties; for properties with format of utc-millisec or date-time; for arrays of any of these types.

Some example JSON Schema property definitions and their corresponding Java field declaration are shown:

JSON Schema	Java
myString : { "type":"string", "default":"abc"}	private String myString = "abc";
myInteger : { "type":"integer", "default":"100"}	private Integer myInteger = 100;
myNumber : { "type":"number", "default":"10.3"}	private Double myNumber = 10.3D;
myMillis : { "type":"string", "format":"utc-millisec", "default":"500"}	private Long myMillis = 500L;
myDate : { "type":"string", "format":"date-time", "default":"500"}	private Date myDate = new Date(500L);
myDate : { "type":"string", "format":"date-time", "default":"2011-02-24T09:25:23.112+0000"}	private Date myDate = new Date(1298539523112L);
myList : { "type":"array", "default":["a","b","c"]}	private List<String> myList = new ArrayList<String>(Arrays.asList("a","b","c"));
As the above table shows, dates can be given a default using either a number of millis since epoch or a date string (ISO 8601 or RFC 1123). In either case, the date will be initialized using a millisecond value in the generated Java type.

title
The 'title' rule appearing in a JSON Schema causes the given title text to be added to the JavaDoc for a property. The title text will appear in JavaDoc comments of the field, getter and setter.

title text always appears before description text.

        description
The description rule appearing in a JSON Schema causes the given description text to be added to the JavaDoc for a property. The description text will appear in JavaDoc comments of the field, getter and setter.

description text always appears after title text.

        format
Using the format rule for a property can influence the Java type chosen to represent your property values. When you use a format value that jsonschema2pojo recognises, it will use a better (more appropriate) type for your Java bean property.

        For example, when I define a JSON property in my schema of type string, if I also attach the rule "format" : "uri" to that property (because my string is actually a URI) then my corresponding Java property will now have the type java.net.URI instead of java.lang.String.

How jsonschema2pojo maps format values to Java types:

Format value	Java type
"date-time"	java.util.Date
"date"	String
"time"	String
"utc-millisec"	long
"regex"	java.util.regex.Pattern
"color"	String
"style"	String
"phone"	String
"uri"	java.net.URI
"email"	String
"ip-address"	String
"ipv6"	String
"host-name"	String
"uuid"	java.util.UUID
anything else (unrecognised format)	type is unchanged
extends
When the extends rule is encountered in your JSON schema (to indicate that one type extends another), this will produce an extends relationship in your generated Java types. That extends value can be a schema given in-line, or referenced using $ref.

As an example, lets imagine a file flower.json with the following content:

        {
        "type" : "object"
        }
and a second file rose.json which contains:

        {
        "type" : "object",
        "extends" : {
        "$ref" : "flower.json"
        }
        }
The two resulting Java types generated from these schemas would be:

public class Flower {
        ....
}
and

public class Rose extends Flower {
        ....
}
Note: the extends rule in JSON schema accepts a schema or an array of schemas - jsonschema2pojo only supports the single schema variant.

        $ref
A '$ref' rule can be used wherever a schema is expected i.e at the root of a schema document, as part of a property definition, as part of the items definition for an array type, as part of an additionalProperties definition.

Supported protocols:

http://, https://
file://
classpath:, resource:, java: (all synonyms used to resolve schemas from the classpath).
Note: If you want to refer to classpath resources from the current Maven module, you will need to bind jsonschema2pojo to a later phase. By default jsonschema2pojo is bound to generate-sources but if you want resources present in the current module to be on the classpath when the plugin executes, then you should bind jsonschema2pojo to the process-resources phase.

        For example, to refer to another document in the same directory which will provide the definition of a 'user' object, you might create a schema like:

        {
        "type" : "object",
        "properties" : {
        "loggedInUser" : {
        "$ref" : "user.json"
        }
        }
        }
        jsonschema2pojo expects $ref values (URIs) to be URLs. Both absolute and relative URLs are valid. You may also refer to part of a schema document using the '#' character followed by a slash or dot delimited path.

        Example using an absolute reference:

        {
        "type" : "object",
        "properties" : {
        "address" : {
        "$ref" : "http://json-schema.org/address"
        }
        }
        }
        Example using a fragment path to reuse a schema definition:

        {
        "type" : "object",
        "properties" : {
        "child1" : {
        "type" : "string"
        },
        "child2" : {
        "$ref" : "#/properties/child1"
        }
        }
        }
        Example treeNode.json using a self reference to build a tree:

        {
        "description" : "Tree node",
        "type" : "object",
        "properties" : {
        "children" : {
        "type" : "array",
        "items" : {
        "$ref" : "#"
        }
        }
        }
        }
        which produces Java code similar to:

        public class TreeNode {

        public List<TreeNode> getChildren() {...}

        public void setChildren(List<TreeNode> children) {...}

        }
        minimum/maximum, minItems/maxItems, minLength/maxLength, required, pattern
        The Maven plugin, CLI and Ant task allow JSR-303 annotations to be activated via a config argument. When activated, the following JSR-303 annotations will be generated:

        Schema rule	Annotation
        maximum	@DecimalMax
        minimum	@DecimalMin
        minItems,maxItems	@Size
        minLength,maxLength	@Size
        pattern	@Pattern
        required	@NotNull
        Extensions
        javaType
        jsonschema2pojo supports an extension property 'javaType' that applies to schemas and allows you to specify a fully qualified name for your generated Java type.

        For example, imagine a schema fooBar.json like:

        {
        "type" : "object"
        }
        When invoking jsonschema2pojo with package argument com.example the generated Java type will have a fully qualified name of com.example.FooBar.

        If the javaType property is added to fooBar.json like:

        {
        "javaType" : "com.other.package.CustomTypeName",
        "type" : "object"
        }
        Then invoking jsonschema2pojo with package argument com.example will result in a generated Java type with a fully qualified name of com.other.package.CustomTypeName.

        The javaType property may appear in any schema definition, not just the root schema in a schema document. For example, this file 'parent.json' invoked using package name com.example:

        {
        "type" : "object",
        "properties" : {
        "myProperty" : {
        "javaType" : "com.other.package.CustomChildName",
        "type" : "object"
        }
        }
        }
        will result in two generated Java types:

        com.example.Parent
        com.other.package.CustomChildName
        existingJavaType
        The existingJavaType property allows existing Java types to be used in your POJOs. The value of existingJavaType should refer to an existing class or interface, and that class/interface will not be generated.

        When referencing existing classes, it's also possible to supply generic type arguments, for instance:

        {
        "type" : "object",
        "properties" : {
        "myProperty" : {
        "existingJavaType" : "java.util.Map<String,Integer>",
        "type" : "object"
        }
        }
        }
        javaEnumNames
        NOTE: You must only use one of javaEnumNames or javaEnums, the later being a more robust extension.

        Any schema that makes use of enum may include javaEnumNames. This property allows you to take control of naming your Java enum constants and avoid relying on auto-generated names.

        If your schema includes javaEnumNames like:

        {
        "type" : "object",
        "properties" : {
        "foo" : {
        "type" : "string",
        "enum" : ["H","L"],
        "javaEnumNames" : ["HIGH","LOW"]
        }
        }
        }
        Then you'll see a generated type like:

        public enum Foo {
        HIGH("H"),
        LOW("L");
        ...
        }
        javaEnums
        NOTE: You must only use one of javaEnumNames or javaEnums, the later being a more robust extension.

        Any schema that makes use of enum may include javaEnums. This property allows you to take control of naming your Java enum constants and avoid relying on auto-generated names and add per-enum constant documentation with title and description.

        If your schema includes javaEnumNames like:

        {
        "$id": "https://cubrc.org/rigors/schemas/common/com/examples/Digits.schema.json",
        "$schema": "http://json-schema.org/draft-07/schema#",
        "type": "string",
        "enum": ["1", "2", "3", "4"],
        "javaEnums": [
        {
        "name": "ONE",
        "title": "The first number.",
        "description": "1 (one, also called unit, unity, and (multiplicative) identity) is a number, and a numerical digit used to represent that number in numerals. (https://en.wikipedia.org/wiki/1)"
        },
        {
        "name": "TWO",
        "title": "The second number.",
        "description": "2 (two) is a number, numeral, and glyph. \n\nIt is the natural number following 1 and preceding 3. (https://en.wikipedia.org/wiki/2)"
        }, {
        "name": "THREE",
        "title": "The third number.",
        "description": "3 (three) is a number, numeral, and glyph. It is the natural number following 2 and preceding 4. (https://en.wikipedia.org/wiki/3)"
        }, {
        "name": "FOUR",
        "title": "The fourth number.",
        "description": "4 (four) is a number, numeral, and glyph. It is the natural number following 3 and preceding 5. (https://en.wikipedia.org/wiki/4)"
        }
        ],
        "additionalProperties": false,
        "required": [],
        "definitions": {}
        }
        Then you'll see a generated type like:

        public enum Digits {

        /**
         * The first number.
         * <p>
         *  1 (one, also called unit, unity, and (multiplicative) identity) is a number, and a numerical digit used to represent that number in numerals. (https://en.wikipedia.org/wiki/1)
         *
         */
        ONE("1"),

        /**
         * The second number.
         * <p>
         *  2 (two) is a number, numeral, and glyph.
         *
         * It is the natural number following 1 and preceding 3. (https://en.wikipedia.org/wiki/2)
         *
         */
        TWO("2"),

        /**
         * The third number.
         * <p>
         *  3 (three) is a number, numeral, and glyph. It is the natural number following 2 and preceding 4. (https://en.wikipedia.org/wiki/3)
         *
         */
        THREE("3"),

        /**
         * The fourth number.
         * <p>
         *  4 (four) is a number, numeral, and glyph. It is the natural number following 3 and preceding 5. (https://en.wikipedia.org/wiki/4)
         *
         */
        FOUR("4");
        ...
        }
        javaInterfaces
        Any schema may include a javaInterfaces property, the value of this property is an array of strings. Each string is expected to contain the fully-qualified name of a Java interface. The Java type generated by the schema will implement all the given interfaces.

        If the javaInterfaces property is added to fooBar.json like:

        {
        "javaInterfaces" : ["java.io.Serializable", "Cloneable"],
        "type" : "object"
        }
        then the result will be a class defined like:

        public class FooBar implements Serializable, Cloneable
        {
        ...
        javaJsonView
        Any schema may include a javaJsonView property, the value of the property is a fully qualified class name. The property causes a @JsonView annotation to be added to the member with the given class for a value. This only works for Jackson-based annotators. It is supported for v1 and v2 Jackson. See Jackson Annotations.

        If javaJsonView is added to fooBar.json schema as follows:

        {
        "type": "object",
        "properties": {
        "internalStatus": {
        "javaJsonView": "com.example.views.Internal",
        "type": "string"
        }
        }
        }
        Then the resulting FooBar.java will have:

        import com.example.views.Internal;

        public class FooBar {
        @JsonView(Internal.class)
        private String internalStatus;
        }
        javaName
        Using javaName rule allows you to define custom names for java bean properties instead of those inferred from the corresponding json property names. This also affects setters and getters.

        For example, the following schema:

        {
        "type": "object",
        "properties": {
        "a": {
        "javaName": "b",
        "type": "string"
        }
        }
        }
        will produce Java code similar to the following:

        public class MyClass {
        @JsonProperty("a")
        private String b;

        @JsonProperty("a")
        public String getB() {
        return b;
        }

        @JsonProperty("a")
        public void setB(String b) {
        this.b = b;
        }
        }
        customPattern/customTimezone
        Set a custom format pattern for date or date-time fields that will be used during serialization (if your binding library supports it). If you're using Jackson 2 this will cause an @JsonFormat annotation to be added. For 'date-time' fields you may also set a custom timezone (if you want to use a timezone other than UTC when writing out the date-time as a string).

        {
        "type" : "object",
        "properties" : {
        "dob" : {
        "type" : "string",
        "format" : "date",
        "customPattern" : "yy-MM-dd"
        },
        "updatedDate" : {
        "type" : "string",
        "format" : "date-time",
        "customDateTimePattern" : "yyyy-MM-dd'T'HH:mm:ssZ",
        "customTimezone": "PDT"
        }
        }
        }
        There are two important things to note before using this extension property:

        If you set the formatDates/formatDateTimes global config options to true, you'll get default ISO-8601/RFC 3339 formatting without having to set these custom properties.
        You should be aware that setting a custom date or date-time pattern may mean that the JSON content you produce is not valid according to the rules of JSON Schema.
        excludedFromEqualsAndHashCode
        You can specify which fields should be omitted in the generated equals() and hashCode() methods using the excludedFromEqualsAndHashCode rule. These fields can be either listed in an object-level array or flagged individually.

        This option is only meaningful if equals() and hashCode() generation is enabled.

        {
        "type": "object",
        "excludedFromEqualsAndHashCode" : [ "excludedByArray" ],
        "properties": {
        "notExcluded" : {
        "type" : "string"
        },
        "excludedByProperty" : {
        "type" : "string",
        "excludedFromEqualsAndHashCode" : true
        }
        }
        In the above example, only notExcluded will be inlcuded in equals() and hashCode().

        © 2012-2018 Joe Littlejohn



