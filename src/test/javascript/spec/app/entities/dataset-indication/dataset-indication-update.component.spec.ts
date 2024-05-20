import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetIndicationUpdateComponent } from 'app/entities/dataset-indication/dataset-indication-update.component';
import { DatasetIndicationService } from 'app/entities/dataset-indication/dataset-indication.service';
import { DatasetIndication } from 'app/shared/model/dataset-indication.model';

describe('Component Tests', () => {
  describe('DatasetIndication Management Update Component', () => {
    let comp: DatasetIndicationUpdateComponent;
    let fixture: ComponentFixture<DatasetIndicationUpdateComponent>;
    let service: DatasetIndicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetIndicationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetIndicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetIndicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetIndicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetIndication(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetIndication();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
