package pw.itr0.selidor.type.mapstruct.bean;

import org.mapstruct.Mapper;
import pw.itr0.selidor.type.mapstruct.MapperConfigs;

@Mapper(config = MapperConfigs.Default.class, uses = ValueObjectTranslator.class)
interface BeanTranslator {

  EntityOne copy(EntityOne s);

  EntityOne translateToEntity(FormOne s);

  FormOne translateToForm(EntityOne s);

  FormOne copy(FormOne s);

  EntityAnother translateToAnother(EntityOne s);

  EntityOne translateToOne(EntityAnother s);

  FormAnother translateToAnother(FormOne s);

  FormOne translateToOne(FormAnother s);

  ValueObjectOne copy(ValueObjectOne s);

  ValueObjectOne translate(NestedFormOne s);

  NestedFormOne translate(ValueObjectOne s);

  NestedFormOne copy(NestedFormOne s);
}
