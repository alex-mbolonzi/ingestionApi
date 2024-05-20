import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { TechnicalDetailsUpdateComponent } from 'app/entities/technical-details/technical-details-update.component';
import { TechnicalDetailsService } from 'app/entities/technical-details/technical-details.service';
import { TechnicalDetails } from 'app/shared/model/technical-details.model';

describe('Component Tests', () => {
  describe('TechnicalDetails Management Update Component', () => {
    let comp: TechnicalDetailsUpdateComponent;
    let fixture: ComponentFixture<TechnicalDetailsUpdateComponent>;
    let service: TechnicalDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [TechnicalDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TechnicalDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TechnicalDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TechnicalDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TechnicalDetails(123);
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
        const entity = new TechnicalDetails();
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
