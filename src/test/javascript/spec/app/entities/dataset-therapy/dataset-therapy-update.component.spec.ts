import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTherapyUpdateComponent } from 'app/entities/dataset-therapy/dataset-therapy-update.component';
import { DatasetTherapyService } from 'app/entities/dataset-therapy/dataset-therapy.service';
import { DatasetTherapy } from 'app/shared/model/dataset-therapy.model';

describe('Component Tests', () => {
  describe('DatasetTherapy Management Update Component', () => {
    let comp: DatasetTherapyUpdateComponent;
    let fixture: ComponentFixture<DatasetTherapyUpdateComponent>;
    let service: DatasetTherapyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTherapyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetTherapyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetTherapyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTherapyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetTherapy(123);
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
        const entity = new DatasetTherapy();
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
