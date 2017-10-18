select concepts.concept_id, concept_name.name
from openmrs.concept concepts, openmrs.concept_name as concept_name
where concepts.concept_id = concept_name.concept_id AND concept_name.locale like 'en' AND concepts.is_set is TRUE;