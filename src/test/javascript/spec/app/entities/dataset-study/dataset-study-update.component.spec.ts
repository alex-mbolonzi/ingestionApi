import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetStudyUpdateComponent } from 'app/entities/dataset-study/dataset-study-update.component';
import { DatasetStudyService } from 'app/entities/dataset-study/dataset-study.service';
import { DatasetStudy } from 'app/shared/model/dataset-study.model';

describe('Component Tests', () => {
  describe('DatasetStudy Management Update Component', () => {
    let comp: DatasetStudyUpdateComponent;
    let fixture: ComponentFixture<DatasetStudyUpdateComponent>;
    let service: DatasetStudyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetStudyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetStudyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetStudyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetStudyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetStudy(123);
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
        const entity = new DatasetStudy();
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
