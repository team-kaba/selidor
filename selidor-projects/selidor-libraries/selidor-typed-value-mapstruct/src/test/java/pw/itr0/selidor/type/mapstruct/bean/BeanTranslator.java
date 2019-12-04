package pw.itr0.selidor.type.mapstruct.bean;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pw.itr0.selidor.type.mapstruct.CridStringToUUID;
import pw.itr0.selidor.type.mapstruct.MapperConfigs;
import pw.itr0.selidor.type.mapstruct.UUIDToCridString;

@Mapper(config = MapperConfigs.Default.class, uses = ValueObjectTranslator.class)
interface BeanTranslator {

  EntityOne copy(EntityOne s);

  EntityOne translateToEntity(FormOne s);

  FormOne translateToForm(EntityOne s);

  FormOne copy(FormOne s);

  EntityAnother translateToAnother(EntityOne s);

  EntityOne translateToOne(EntityAnother s);

  @SuppressWarnings("unused") // mapstruct-processor がコードを生成できて、 compile できることの確認。
  FormAnother translateToForm(EntityAnother s);

  @SuppressWarnings("unused") // mapstruct-processor がコードを生成できて、 compile できることの確認。
  EntityAnother translateToEntity(FormAnother s);

  @SuppressWarnings("unused") // mapstruct-processor がコードを生成できて、 compile できることの確認。
  EntityAnother copy(EntityAnother s);

  @Mapping(target = "id", qualifiedBy = CridStringToUUID.class)
  FormAnother translateToAnother(FormOne s);

  @Mapping(target = "id", qualifiedBy = UUIDToCridString.class)
  FormOne translateToOne(FormAnother s);

  ValueObjectOne copy(ValueObjectOne s);

  ValueObjectOne translate(NestedFormOne s);

  NestedFormOne translate(ValueObjectOne s);

  NestedFormOne copy(NestedFormOne s);
}
