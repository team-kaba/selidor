package pw.itr0.selidor.type.mapstruct;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

public interface MapperConfigs {

  @MapperConfig(
      uses = TypedValueTranslator.class,
      unmappedTargetPolicy = ReportingPolicy.ERROR,
      typeConversionPolicy = ReportingPolicy.ERROR,
      collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
      nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      injectionStrategy = InjectionStrategy.CONSTRUCTOR)
  interface Default {}

  @MapperConfig(
      uses = TypedValueTranslator.class,
      unmappedTargetPolicy = ReportingPolicy.ERROR,
      typeConversionPolicy = ReportingPolicy.ERROR,
      componentModel = "spring",
      collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
      nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      injectionStrategy = InjectionStrategy.CONSTRUCTOR)
  interface Spring {}

  @MapperConfig(
      uses = TypedValueTranslator.class,
      unmappedTargetPolicy = ReportingPolicy.ERROR,
      typeConversionPolicy = ReportingPolicy.ERROR,
      componentModel = "cdi",
      collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
      nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      injectionStrategy = InjectionStrategy.CONSTRUCTOR)
  interface CDI {}

  @MapperConfig(
      uses = TypedValueTranslator.class,
      unmappedSourcePolicy = ReportingPolicy.ERROR,
      unmappedTargetPolicy = ReportingPolicy.ERROR,
      typeConversionPolicy = ReportingPolicy.ERROR,
      componentModel = "jsr330",
      collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
      nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
      injectionStrategy = InjectionStrategy.CONSTRUCTOR)
  interface JSR330 {}
}
