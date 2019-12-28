# Srnjak HATEOAS library
Hateoas utility for JAX-RS environment. It's goal is to decorate an existent 
entity with data used for hypermedia.

At the moment it supports `application/hal+json` and `application/hal+xml` 
media types only.

## Build
The library uses Maven as build tool.

To build and install into local repository use the following command:

    mvn clean install

## Usage
For creating hateoas output, all you need is to add the library as your project 
dependency.

Mark resources to produce one or more of the supported media types. Wrap an 
object or collection of object into the specific hypermedia model and set it as 
entity on response.

If there is unsupported media type listed, unwrapped object will be written in 
a endpoint response.

### Single object
    
    @Path("/single")
    @GET
    @Produces({                                                            // #1
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            HalMediaType.APPLICATION_HAL_JSON,
            HalMediaType.APPLICATION_HAL_XML})
    public Response get() {

        MyEntity myEntity = ... ;

        EntityModel<MyEntity> entityModel = new EntityModel<>(myEntity);   // #2
        entityModel.addLink(Link.builder()                                 // #3
                .relation(IanaLinkRelation.SELF)
                .href("http://www.example.com/")
                .build());
        
        return Response.ok(entityModel).build();                           // #4
    }
 
1. List the supported media types which endpoint is able to produce.
2. Wrap object into EntityModel (which is subclass of HypermediaModel).
3. Add some links to the model.
4. Set the wrapping model as entity to the response.

### Collection

    @GET
    @Produces({                                                            // #1
              MediaType.APPLICATION_JSON,
              MediaType.APPLICATION_XML,
              HalMediaType.APPLICATION_HAL_JSON,
              HalMediaType.APPLICATION_HAL_XML})
    public Response getAll() {

        List<MyEntity> myEntities = ... ;

        List<EntityModel<TestEntity>> emList = myEntities.stream()         // #2
                .map(e -> {
                    EntityModel<TestEntity> entityModel = new EntityModel<>(e);
                    entityModel.addLink(Link.builder()
                            .relation(IanaLinkRelation.SELF)
                            .href("http://www.example.com/" + e.getId())
                            .build());
                    return entityModel;
                })
                .collect(Collectors.toUnmodifiableList());

        CollectionModel<MyEntity> collectionModel =                        // #3
                new GenericJaxrsCollectionModel<>(
                        emList, new GenericType<>(){});

        collectionModel.addLink(                                           // #4
                Link.builder()
                        .relation(IanaLinkRelation.SELF)
                        .href("http://www.example.com/")
                        .build());

        return Response.ok(collectionModel).build();                       // #5
    }

1. List the supported media types which endpoint is able to produce.
2. Convert list of objects into list of hypermedia models.
3. Wrap list of hypermedia models into CollectionModel (which is subclass of
 HypermediaModel).
4. Add some links to the model.
5. Set the wrapping model as entity to the response.
 
