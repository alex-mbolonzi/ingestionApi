import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetUserUsageRestrictionUpdateComponent } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction-update.component';
import { DatasetUserUsageRestrictionService } from 'app/entities/dataset-user-usage-restriction/dataset-user-usage-restriction.service';
import { DatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

describe('Component Tests', () => {
  describe('DatasetUserUsageRestriction Management Update Component', () => {
    let comp: DatasetUserUsageRestrictionUpdateComponent;
    let fixture: ComponentFixture<DatasetUserUsageRestrictionUpdateComponent>;
    let service: DatasetUserUsageRestrictionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetUserUsageRestrictionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetUserUsageRestrictionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetUserUsageRestrictionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetUserUsageRestrictionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetUserUsageRestriction(123);
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
        const entity = new DatasetUserUsageRestriction();
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
